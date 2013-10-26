package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.sitemap.Loc._
import net.liftweb.http._
import net.liftweb.http.SHtml._
import net.liftmodules.FoBoBs.mapper._

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] with BootstrapMegaMetaProtoUser[User]  {
  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  // define the order fields will appear in forms and output
  override def fieldOrder = List(id, firstName, lastName, email,
  locale, timezone, password, textArea) 
  
  // comment this line out to require email validations
  override def skipEmailValidation = true

  //add a loc group to the user menu
  override def globalUserLocParams: List[LocParam[Unit]] = List(LocGroup("user"))
  override def resetPasswordMenuLoc: Box[net.liftweb.sitemap.Menu] = Box(Empty) 
  override def validateUserMenuLoc: Box[net.liftweb.sitemap.Menu] = Box(Empty) 
 
}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends MegaProtoUser[User] {
  def getSingleton = User // what's the "meta" server

  // define an additional field for a personal essay
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Personal Essay"
  }
}

