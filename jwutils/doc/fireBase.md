1 Legacy
  ['legəsɪ]  英['legəsɪ]  美['lɛgəsi]
  le ga cy 
  n. 遗赠，遗产
  
  网络释义:
  Legacy - 遗产; 力狮; 遗赠
  Subaru Legacy - 速霸陆Legacy; 斯巴鲁力狮; 富士力狮
  Emerald Legacy - 父亲留下的祖母绿; 绿宝石; 绿宝石遗产
2 Deprecated
  [ˈdeprəkeɪtɪd]  英[ˈdeprəkeɪtɪd]  美['dɛprə,ketɪd]
  de pre ca ted 
  v. 不赞成；弃用；不宜用（deprecate的过去式及过去分词形式）
  
  网络释义:
  deprecated - 弃用; 废弃; 不赞成
  Deprecated Value - 废弃价值
  Deprecated feature - 不建议使用该特性; 废弃的特性  
3 Migration
  [maɪ'greɪʃ(ə)n]  英[maɪ'greɪʃ(ə)n]  美[maɪ'ɡreʃən]
  mi gra tion
  n. 迁移；移民；移动
  
  网络释义:
  migration - 迁移; 迁徙; 移民
  human migration - 人口迁徙; 人类迁居; 移民
  Migration Period - 欧洲民族大迁徙; 迁徙周期; 运聚期次
    
  
 You're viewing the legacy docs. They are deprecated as of May 18, 2016.
 你正在阅读个遗产文档，它们在6.18已经被废弃
These docs are for version 2.5.2 and below of the Java SDK. Go to our current docs, or see our Android migration guide.
5 minute quickstart
Android Quickstart
Sign Up & Start Hacking
Set Up Firebase for Android (2:51)
Create an account

The first thing you need to do to get started with Firebase is sign up for a free account. A brand new Firebase app will automatically be created for you with its own unique URL ending in firebaseio.com. We'll use this URL to store and sync data.
Install Firebase

To use Firebase features in your Android application you can add a dependency to Gradle or Maven in your project or download the latest SDK.
As of version 1.1, Firebase has different SDKs for Android and the JVM. This sections demonstrates Android. If you are developing on the JVM please add a dependency to firebase-client-jvm instead.
Using Gradle or Maven

We publish builds of our Android and Java SDK to the Maven central repository. To install the library inside Android Studio, you can simply declare it as dependency in your build.gradle file:

    dependencies {
        compile 'com.firebase:firebase-client-android:2.5.2+'
    }

If you are getting a build error complaining about duplicate files you can choose to exclude those files by adding the packagingOptions directive to your build.gradle file:

    android {
        ...
        packagingOptions {
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/LICENSE-FIREBASE.txt'
            exclude 'META-INF/NOTICE'
        }
    }

If you use Maven to build your application, you can add the following dependency to your pom.xml:

    <dependency>
      <groupId>com.firebase</groupId>
      <artifactId>firebase-client-android</artifactId>
      <version>[2.5.2,)</version>
    </dependency>

Download and Install
There is also a plain Java SDK for the JVM available.

You can grab the latest Firebase SDK here:
Download Firebase Android SDK

After downloading the JAR, place it on your application's classpath. Typically, this is in your libs folder. Depending on your IDE, you may need to explicitly add the library to your project as a dependency.
Add Android Permissions

The Firebase library requires the android.permission.INTERNET permission to operate. Your app will not work unless you add this permission to your AndroidManifest.xml file:

    <uses-permission android:name="android.permission.INTERNET" />

Setup Firebase on Android
This step is only required on Android and not necessary on the JVM.

The Firebase library must be initialized once with an Android context. This must happen before any Firebase app reference is created or used. You can add the setup code to your Android Application's or Activity's onCreate method.

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // other setup code
    }

Read & Write to your Firebase Database

To read and write data from your Firebase database, we need to first create a reference to it. We do this by passing the URL of your database into the Firebase constructor:

    Firebase myFirebaseRef = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com/");

There are several other ways to create a reference to a location in your database. These are all explained in our Understanding Data document.
Writing Data

Once we have a reference to your data, we can write any Boolean, Long, Double, Map<String, Object> or List object to it using setValue():

    myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");

Our documentation on saving data explains the different write methods our API offers and how to know when the data has been successfully written to your Firebase database.
Reading Data

Reading data from your Firebase database is accomplished by attaching an event listener and handling the resulting events. Assuming we already wrote to myFirebaseRef above, we can retrieve the message value by using the addValueEventListener method:

    myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
      }
      @Override public void onCancelled(FirebaseError error) { }
    });

Call getValue() on the DataSnapshot returned from the callback to access the returned object.

In the example above, the value event will fire once for the initial state of the data, and then again every time the value of that data changes. You can learn more about the various event types and how to handle event data in our documentation on reading data.
Authenticate Your Users

Firebase provides full support for authenticating users with Email & Password, Facebook, Twitter, GitHub, Google, or your existing authentication system.

To get started with Email & Password auth, enable the Email & Password provider in your Firebase app's dashboard:

    Choose the Login & Auth tab.
    Select the Email & Password tab and enable authentication.

Now that the authentication provider is enabled you can create a new user:

    myFirebaseRef.createUser("bobtony@firebase.com", "correcthorsebatterystaple", new Firebase.ValueResultHandler<Map<String, Object>>() {
        @Override
        public void onSuccess(Map<String, Object> result) {
            System.out.println("Successfully created user account with uid: " + result.get("uid"));
        }
        @Override
        public void onError(FirebaseError firebaseError) {
            // there was an error
        }
    });

Once you've created your first user, you can log them in using the authWithPassword method.

Learn how to authenticate via Facebook, Twitter, Google or your own custom system in our User Authentication guide.
Secure Your Data

Use our powerful expression-based Security and Firebase Rules to control access to your data and validate input:

    {
      ".read": true,
      ".write": "auth.uid === 'admin'",
      ".validate": "newData.isString() && newData.val().length < 500"
    }

Firebase enforces your Security and Firebase Rules consistently whenever data is accessed. The rules language is designed to be both powerful and flexible, so that you can maintain fine-grained control over your application's data.
What's Next?

    Read the Development Guide
    View the full Java API
    Explore our code examples

