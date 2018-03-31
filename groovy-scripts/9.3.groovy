//edit nexus user
def user = security.securitySystem.getUser('nexus')
user.setEmailAddress('nexus@nexus.com')
security.securitySystem.updateUser(user)
security.securitySystem.changePassword('nexus','nexus')
//create new user
security.addUser("test_user","test", "user","test@test.com", true,"test", [ 'nx-my-role' ])
//new_repo in my blob
repository.createMavenHosted("test_repo", "my_blob")
