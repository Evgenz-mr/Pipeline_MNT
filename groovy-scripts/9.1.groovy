/*Scrypt get 2 arguments. 1st - type of operation ("push"/"pull")*/
/*2nd - artifact name. Artifact must plase in root directory with the scrypt*/
/*PLEASE USE ARTIFACT_NAME WITH TEMPLATE GROUP_ID-ARTIFACT_ID-VERSION.extension*/
/*FOR EXAMPLE my_group_id-hello_war-8.tar.gz*/

def ACT = args[0]
def  ART_NAME= args[1]
/*PLEASE NEVER DO THIS. EVERYTIME TAKE STEPS TO HIDE YOUR CREDENTIALS */
def MY_CREDS = "LOGIN:PASSWORD"
def MY_REPO = "my_repo"
def NEXUS_PATH = "NEXUS_ADDRESS"
def GROUP_ID = ART_NAME.substring(0, ART_NAME.indexOf("-"))
def ART_ID = ART_NAME.substring(ART_NAME.indexOf("-")+1, ART_NAME.lastIndexOf("-"))
def VER = ART_NAME.substring(ART_NAME.lastIndexOf("-")+1, ART_NAME.indexOf("."))
def FILE_NAME = ART_NAME.substring(ART_NAME.indexOf("-")+1, ART_NAME.lastIndexOf("-"))
def EXT = ART_NAME.substring(ART_NAME.indexOf(".")+1)
def CONVERTED_CREDS = "${MY_CREDS}".getBytes().encodeBase64().toString()

if("$ACT"=="push"){
    def File = new File ("${ART_NAME}").getBytes()
    def CONNECTION = new URL("http://${NEXUS_PATH}/repository/${MY_REPO}/${GROUP_ID}/${ART_ID}/${VER}/${FILE_NAME}-${VER}.${EXT}").openConnection()
    CONNECTION.setRequestProperty("Authorization" , "Basic ${CONVERTED_CREDS}")
    CONNECTION.setRequestMethod("PUT")
    CONNECTION.doOutput = true
    CONNECTION.setRequestProperty( "Content-Type", "application/x-gzip" )
    def writer = new DataOutputStream(CONNECTION.outputStream)
    writer.write(File)
    writer.close()
    println CONNECTION.responseCode
}

else {
    new File("${ART_NAME}").withOutputStream { out ->
        def url = new URL("http://${NEXUS_PATH}/repository/${MY_REPO}/${GROUP_ID}/${ART_ID}/${VER}/${FILE_NAME}-${VER}.${EXT}").openConnection()
        def remoteAuth = "Basic " + "${MY_CREDS}".bytes.encodeBase64()
        url.setRequestProperty("Authorization", remoteAuth);
        out << url.inputStream
    }
}
