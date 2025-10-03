   import jenkins.model.Jenkins
   import hudson.security.HudsonPrivateSecurityRealm
   import hudson.security.AuthorizationStrategy
   import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
   import hudson.security.ProjectMatrixAuthorizationStrategy
   import hudson.security.UserMayOrMayNotExistException
   import hudson.tasks.Mailer
   import hudson.model.User
   import hudson.security.SecurityRealm

   // Disable anonymous access
   Jenkins.instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
   
   // Create authorization strategy
   def strategy = new ProjectMatrixAuthorizationStrategy()
   strategy.add(Jenkins.ADMINISTER, "admin")
   strategy.add(Jenkins.READ, "pipeline-user")
   strategy.add(hudson.model.Item.BUILD, "pipeline-user")
   strategy.add(hudson.model.Item.READ, "pipeline-user")
   strategy.add(hudson.model.Item.WORKSPACE, "pipeline-user")
   Jenkins.instance.setAuthorizationStrategy(strategy)

   // Create non-admin user for pipeline
   def user = User.get("pipeline-user", true)
   user.setFullName("Pipeline User")
   user.addProperty(new Mailer.UserProperty("pipeline-user@example.com"))
   user.save()

   // Create admin user
   def admin = User.get("admin", true)
   admin.setFullName("Jenkins Admin")
   admin.addProperty(new Mailer.UserProperty("admin@example.com"))
   admin.save()

   println "Security configuration applied successfully"