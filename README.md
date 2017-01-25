# GDXShooter setup

## Install Git
https://git-scm.com/downloads

## Making sure JDK is set up in IntelliJ IDEA
1. If there is any project open close it.
  * *File -> Close Project*
2. Go to the project structure menu.
  * *Configure -> Project Defaults -> Project Structure*
3. On the Project tab, make sure there is an SDK (JDK) selected.
4. If not, click "New", select "JDK", and point it to the directory that has the JDK on it
  * default location is *Program Files -> Java -> jdk.xyz_123*
5. If nothing is there, ask yourself how you thought this would ever work without the JDK, then download it from the Oracle website and start over.

## Getting project in IntelliJ IDEA
1. If there is any project open close it.
  * *File -> Close Project*
2. Check out the project from Git.
       VCS -> *Check out from Version Control -> Git*
3. In the following window, enter "https://github.com/LeastCreative/GDXShooter" for the git repository URL.
4. If the window pops up asking you to open the IDEA project, click Yes... obviously.
5. Don't change anything on the "Import from Gradle" window, and click OK.
6. The project should start building. You will need to allow IntelliJ to access the internet, this can take some time.
7. When the module menu pops up, make sure all of the modules are checked and click OK.
8. The project should now be open; a warning about "unindexed maven repositories" will appear. Allow it a few minutes to clear.
9. Wait for Step 8 above to complete before proceeding.

## Configuring the project to work correctly
1. In the strip of icons in the top right, the second one in should look like an empty select list, because that's what it is. Click it and select "Edit Configurations"
2. Click the plus sign in the top left of the new window to add a new config. This will be the desktop config, so select "Application"
3. A new "unnamed" config should pop up. Name it something that makes sense; I named mine "Desktop" because I'm not an idiot.
4. If the "Main class" field is not automatically filled in click the dots button and select: 
  * *GDXShooter -> desktop -> src -> com.supershooter -> DesktopLauncher*
5. Click the dots next to the Working Directory and select: 
  * *GDXShooter -> core -> assets*
6. In the menu for "Use classpath of module:" select "desktop_main" and click OK.
7. The Desktop config should now be populated in the config select list on the upper right.
8. Click the play button to view a stupid clipart I made online and some animated squares. If there is no clipart or animated squares, you did something wrong, learn to follow directions and start over.
9. There is a chance that you might be looking at what is basically a blank screen, if so, press ALT+1 to switch to project view.
