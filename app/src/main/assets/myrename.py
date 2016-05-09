import os
import re

def renameFiles(doItReally):
    files = os.listdir(".")
    l = len(files)
    for i in range(l):
        file = str(files[i])
        if file[:3].isdigit() == False:
            continue
        ending = file[-3:]
        if ending == "mp3":
            #print file
            filenew = file[4].upper() + file[5:]
            print "filenew: ", filenew
            png = str()
            found = False
            if i < l:
                temp = files[i+1][:3]
                if file[:3] == temp:
                    png = files[i+1]
                    found = True
            if found == False and i != 0:
                temp = files[i-1][:3]
                if file[:3] == temp:
                    png = files[i-1]
                    found = True
            if found:
                #print png
                pass
            else:
                print "not found for: ",file
                return False
            pngnew = filenew[:-3] + "png"
            print "newpng: ", pngnew
            print "-------------------"
            if doItReally:
                os.rename(file,filenew)
                os.rename(png,pngnew)
    return True

if renameFiles(False):
    renameFiles(True)
