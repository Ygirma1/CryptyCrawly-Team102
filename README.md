# CS2340-Team102-Dungeon-Crawler


## Setup guide


### I. Download JavaFX SDK (if you haven't)

- Download the latest [JavaFX SDK](https://gluonhq.com/products/javafx/) suitable with your system here.
- Unpack the zip file and put the folder into a folder where you won't accidentally delete it.
- For example: **/Users/your_name/Desktop/javafx-sdk-11**

### II. Check if IntelliJ enabled JavaFX plugin already

- Go to **File/Settings/Plugins** 
- Go to the **Installed** tab and search for **"JavaFX"**. If you find it, make sure it's enabled.
- Apply changes

### Clone and open this repo locally

```
git clone https://github.gatech.edu/cdong49/CS2340-Team102-Dungeon-Crawler
```

- Go to **File/Open** on IntelliJ and choose the folder.

### Add JavaFX Library

- Go to **File/Project Structure**
- Click on the **Libraries** section, click **+**, and select **Java**
- Choose the path to the **lib** folder in the JavaFX SDK earlier.
- For example: **/Users/your_name/Desktop/javafx-sdk-11/lib**
- Click Ok and apply changes
- Go to the **Modules** section from Project Structure.
- Check this box for the newly added library.

![alt text](/setup_resources/addLib.png)

- Apply and close

### Add VM

- Go to **Run/Edit Configurations**
- In **Aplication/Controller**, select your current Java JDK under **Build and Run**.
- Click on **Modify options/Add VM option**
- Paste this into the VM option with the correct JavaFX SDK lib path.

```
--module-path /Users/your_name/Desktop/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml
```


- It should now look something like this, and you can just apply and close. 

![alt text](/setup_resources/Config.png)

- Test run the Controller file to make sure it works.
