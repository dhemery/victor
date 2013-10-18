#!/usr/bin/env bash
#
# USAGE: runsim.sh <path to ios app> <device type>
#
# The path to the application to launch
APPLICATION_BUNDLE_PATH="$1"

# The device type to use.
DEVICE_TYPE="$2"

# Get the executable name
APPLICATION_PLIST_PATH="$APPLICATION_BUNDLE_PATH/Info"
APPLICATION_EXECUTABLE_NAME=`defaults read "$APPLICATION_PLIST_PATH" CFBundleExecutable`
APPLICATION_EXECUTABLE_PATH="$APPLICATION_BUNDLE_PATH/$APPLICATION_EXECUTABLE_NAME"

# The path to the Xcode tools.
XCODE_DEVELOPER_PATH=`xcode-select --print-path`

# The path to the current Xcode content.
XCODE_CONTENT_PATH="$XCODE_DEVELOPER_PATH/.."

# The absolute path to the Automation template.
AUTOMATION_TEMPLATE_PATH="$XCODE_CONTENT_PATH/Applications/Instruments.app/Contents/PlugIns/AutomationInstrument.bundle/Contents/Resources/Automation.tracetemplate"

# The path to this script
DIR=$( cd $( dirname "$0" ) && pwd )

# The path to the script that loops for Instruments
LOOP_FOREVER="$DIR/loop-forever.js

# The prefix of the directory where instruments will write its output.
# TODO: Make this a parameter. It should probably be an absolute path.
OUTPUT_PREFIX=instruments.output

# Write device type to simulator plist
defaults write com.apple.iphonesimulator SimulateDevice "'$DEVICE_TYPE'"

# Launch the app and run the "loop forever" script
instruments -D "$OUTPUT_PREFIX" -t "AUTOMATION_TEMPLATE_PATH" "$APPLICATION_EXECUTABLE_PATH" -e UIASCRIPT "$LOOP_FOREVER"
