This folder contains the libraries and other content to add a
Frank server to your app:
   - libFrank.a: The Frank server. This includes a built-in
     selector engine called 'uiquery'.
   - libShelley.a: A selector engine called 'shelley_compat'.
   - libigor-0.4.0.a: The Igor selector engine, which finds views
     using a CSS-like syntax.
   - frank_static_resources.bundle: HTML and Javascript files
     for the Symbiote server, which allows you to explore your
     app's view hierarchy and view properties.

To add the Frank server (and the Igor selector engine) to your
application:

   1. Copy this entire folder into your app's Xcode project.
      (You may omit this README.txt file if you wish.)

   2. In the Link Binary with Libraries build phase, add
      libFrank.a, libShelley.a, and libigor-0.4.0.a.

   3. In the Copy Bundle Resources build phase, add the
      frank_static_resources.bundle.

   4. In Build Settings, in the Linking section, edit the
      Other Linker Flags setting to include -ObjC and -all_load.

   5. Rebuild your application.

To verify your installation, launch your app in the simulator,
then use any browser to visit localhost:37265. If you see
A display of your app's view hierarchy, your installation is
okay.
