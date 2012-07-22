#!/usr/bin/env osascript
on run argv
    set applicationName to item 1 of argv
    set keys to "\"" & item 2 of argv & "\""
    set metakeys to item 3 of argv
    activate application applicationName
	tell application "System Events"
	    keystroke keys using metakeys
	end
#	"keystroke " & keys & " using " & metakeys
end
