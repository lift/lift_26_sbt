package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.http._
import net.liftweb.http.SHtml._


/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] {
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
  //override menu loc's for localization and glyphicons
  override def createUserMenuLoc: Box[Menu] =
    Full(Menu(Loc("CreateUser" + menuNameSuffix, signUpPath, S.loc("sign.up" , scala.xml.Text("sign.up")) /*S.?("sign.up")*/, createUserMenuLocParams ::: globalUserLocParams)))
  override def lostPasswordMenuLoc: Box[Menu] =
    Full(Menu(Loc("LostPassword" + menuNameSuffix, lostPasswordPath, S.loc("lost.password" , scala.xml.Text("lost.password"))/*S.?("lost.password")*/, lostPasswordMenuLocParams ::: globalUserLocParams))) // not logged in
  override def loginMenuLoc: Box[Menu] =
    Full(Menu(Loc("Login" + menuNameSuffix, loginPath, S.loc("login" , scala.xml.Text("login")) /*S.?("login")*/, loginMenuLocParams ::: globalUserLocParams)))
  override def logoutMenuLoc: Box[Menu] =
    Full(Menu(Loc("Logout" + menuNameSuffix, logoutPath, S.loc("logout" , scala.xml.Text("logout")) /*S.?("logout")*/, logoutMenuLocParams ::: globalUserLocParams)))
//  override def resetPasswordMenuLoc: Box[Menu] =
//    Full(Menu(Loc("ResetPassword" + menuNameSuffix, (passwordResetPath, true), S.loc("reset.password" , scala.xml.Text("reset.password")) /*S.?("reset.password")*/, resetPasswordMenuLocParams ::: globalUserLocParams))) //not Logged in
  override def editUserMenuLoc: Box[Menu] =
    Full(Menu(Loc("EditUser" + menuNameSuffix, editPath,  S.loc("edit.user" , scala.xml.Text("edit.user")) /*S.?("edit.user")*/, editUserMenuLocParams ::: globalUserLocParams)))
  override def changePasswordMenuLoc: Box[Menu] =
    Full(Menu(Loc("ChangePassword" + menuNameSuffix, changePasswordPath, S.loc("change.password" , scala.xml.Text("change.password"))/*S.?("change.password")*/, changePasswordMenuLocParams ::: globalUserLocParams)))

  //some ProtoUser view data overrides for bootstrap styling 
  override def signupXhtml(user: TheUserType) = {
   (<form method="post" action={S.uri}><table class="table table-striped"><thead><tr><td
              colspan="2">{ S.?("sign.up") }</td></tr></thead><tbody>
          {localForm(user, false, signupFields)}
          <tr><td>&nbsp;</td><td><user:submit/></td></tr></tbody>
                                        </table></form>)    
  }

  override def loginXhtml = {
    (<form method="post" action={S.uri}><table class="table table-striped"><thead><tr><td
              colspan="2">{S.?("log.in")}</td></tr></thead><tbody>
          <tr><td>{userNameFieldString}</td><td><user:email /></td></tr>
          <tr><td>{S.?("password")}</td><td><user:password /></td></tr>
          <tr><td><a href={lostPasswordPath.mkString("/", "/", "")}
                >{S.?("recover.password")}</a></td><td><user:submit /></td></tr></tbody></table>
     </form>)
  }
  
  override def lostPasswordXhtml = {
    (<form method="post" action={S.uri}>
        <table class="table table-striped"><thead><tr><td
              colspan="2">{S.?("enter.email")}</td></tr></thead><tbody>
          <tr><td>{userNameFieldString}</td><td><user:email /></td></tr>
          <tr><td>&nbsp;</td><td><user:submit /></td></tr>
        </tbody></table>
     </form>)
  }
  
  override def editXhtml(user: TheUserType) = {
    (<form method="post" action={S.uri}>
        <table class="table table-striped"><thead><tr><td colspan="2">{S.?("edit")}</td></tr></thead><tbody>
          {localForm(user, true, editFields)}
          <tr><td>&nbsp;</td><td><user:submit/></td></tr>
        </tbody></table>
     </form>)
  }
  
  override def changePasswordXhtml = {
    (<form method="post" action={S.uri}>
        <table class="table table-striped"><thead><tr><td colspan="2">{S.?("change.password")}</td></tr></thead><tbody>
          <tr><td>{S.?("old.password")}</td><td><user:old_pwd /></td></tr>
          <tr><td>{S.?("new.password")}</td><td><user:new_pwd /></td></tr>
          <tr><td>{S.?("repeat.password")}</td><td><user:new_pwd /></td></tr>
          <tr><td>&nbsp;</td><td><user:submit /></td></tr>
        </tbody></table>
     </form>)
  }
  
  override def standardSubmitButton(name: String,  func: () => Any = () => {}) = {
    SHtml.submit(name, func,("class","btn btn-default"))
  }

 
  
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

