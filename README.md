# C4N_Weather
Weather Web App for CS370  

Changes/setup implemented:  

1. Changed folder structure to include Controllers/Repositories/Services folders and included a README.md file within each explaining their purpose.  
2. Moved HelloController.java into /Controllers folder.  
3. Changed HelloController.java from @RestController to @Controller in order to load a basic HTML page instead of simple text. For any HTML files, @Controller will need to be used.  
4. Changed HelloController.java text to return "login" which returns login.html.
5. Created a new file login.html and placed it main/resources/templates folder. Supposedly css//images/other static (unchanging at runtime) files should go in resources/static, where .html files should go in resources/templates. IDK tbh it worked regardless of which folder the .html file was in so we'll just put it in templates for now unless we run into issues.  
6. Added a property in resources/application.properties such that making changes to your code will not require reloading the server to see the affected changes on localhost, they will automatically update after a few seconds after saving the changes.  
7. Added a dependency for ThymeLeaf HTML support and a dependency for devtools for the reloading mentioned in step 6 in the pom.xml file. This is where we will continue adding dependencies as we need them.  

Basic Instructions for getting project running:  
1. Pull this repository onto your local machine.  
2. Open this project in VSCode.  
3. Open terminal in VSCode, and if you are not already in the c4n_weather folder, cd into c4n_weather. This gets you into the actual project path.  
4. Type into the console "./mvnw springboot:run" and press enter. Let it run, it takes about 45 seconds to launch the server. If you run into any errors let the group know in the discord chat and we can solve it. I (Jason) ran into a handful of issues with my JAVA_HOME path so I can help you work through them.  
5. Once the server is up and running, VSCode will prompt you that your application is running on port 8080, either click the button to launch it, or go to your internet browser and enter "localhost:8080" into the url. The website should now display.  

That should be it! If there is any more questions don't hesitate to ask, I highly doubt everything is perfect but it was the best I was able to get set up as soon as I could so anyone can get to work.