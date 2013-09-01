package code.lib

import net.liftweb.util.Helpers._
import net.liftweb.http.S
import net.liftweb.common._
import net.liftweb.proto.{ProtoUser => GenProtoUser}
import net.liftweb.mapper._

trait BootstrapMegaMetaProtoUser[ModelType <: MegaProtoUser[ModelType]] extends KeyedMetaMapper[Long, ModelType] with GenProtoUser {
  self: ModelType =>
  
  override def loginXhtml = {
     <form class="form-horizontal" role="form" action={S.uri} method="post">
       <legend>{S.?("log.in")}</legend>
      <div class="form-group">
        <label for="username" class="col-lg-2 control-label">{userNameFieldString}</label>
        <div class="col-lg-10">
          <input type="email" class="form-control" id="username" name="username" placeholder={userNameFieldString} autofocus="autofocus" />
        </div>
      </div>
      <div class="form-group">
        <label for="password" class="col-lg-2 control-label">{S.?("password")}</label>
        <div class="col-lg-10">
          <input type="password" class="form-control" id="password" name="password" placeholder={S.?("password")} />
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-10">
          <a href={lostPasswordPath.mkString("/", "/", "")}>{S.?("recover.password")}</a>
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-offset-2 col-lg-10">
          <button type="submit" class="btn btn-default">Sign in</button>
        </div>
      </div>
    </form>
  }
  
  
   override def login = {
    if (S.post_?) {
      S.param("username").
      flatMap(username => findUserByUserName(username)) match {
        case Full(user) if user.validated_? &&
          user.testPassword(S.param("password")) => {
            val preLoginState = capturePreLoginState()
            val redir = loginRedirect.get match {
              case Full(url) =>
                loginRedirect(Empty)
                url
              case _ =>
                homePage
            }

            logUserIn(user, () => {
              S.notice(S.?("logged.in"))

              preLoginState()

              S.redirectTo(redir)
            })
          }

        case Full(user) if !user.validated_? =>
          S.error(S.?("account.validation.error"))

        case _ => S.error(S.?("invalid.credentials"))
      }
    }
    
    ("type=submit" #> loginSubmitButton(S.?("log.in"))).apply(loginXhtml)
  }
}

