import jenkins.model.Jenkins
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.AuthorizationStrategy
import hudson.security.ProjectMatrixAuthorizationStrategy
import hudson.model.User
import hudson.tasks.Mailer

// Disable anonymous access
Jenkins.instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))

// Create authorization strategy
def strategy = new ProjectMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin")
strategy.add(Jenkins.ADMINISTER, "roshanshrestha")
strategy.add(Jenkins.READ, "pipeline-user")
strategy.add(Jenkins.READ, "roshanshrestha")
strategy.add(hudson.model.Item.BUILD, "pipeline-user")
strategy.add(hudson.model.Item.BUILD, "roshanshrestha")
strategy.add(hudson.model.Item.READ, "pipeline-user")
strategy.add(hudson.model.Item.READ, "roshanshrestha")
strategy.add(hudson.model.Item.WORKSPACE, "pipeline-user")
strategy.add(hudson.model.Item.WORKSPACE, "roshanshrestha")
strategy.add(hudson.model.Item.CONFIGURE, "roshanshrestha")
strategy.add(hudson.model.Item.CREATE, "roshanshrestha")
strategy.add(hudson.model.Item.DELETE, "roshanshrestha")
strategy.add(hudson.model.Item.DISCOVER, "roshanshrestha")
strategy.add(hudson.model.Item.EXTENDED_READ, "roshanshrestha")
strategy.add(hudson.model.Item.WORKSPACE, "roshanshrestha")
Jenkins.instance.setAuthorizationStrategy(strategy)

// Create non-admin user for pipeline
def user = User.get("pipeline-user", true)
user.setFullName("Pipeline User")
user.addProperty(new Mailer.UserProperty("pipeline-user@example.com"))
user.save()

// Create admin user
def admin = User.get("admin", true)
admin.setFullName("Jenkins Admin")
admin.addProperty(new Mailer.UserProperty("roshanshresthapnk@gmail.com"))
admin.save()

// Create roshanshrestha user
def roshan = User.get("roshanshrestha", true)
roshan.setFullName("Roshan Shrestha")
roshan.addProperty(new Mailer.UserProperty("roshanshresthapnk@gmail.com"))
roshan.save()

println "Security configuration applied successfully"