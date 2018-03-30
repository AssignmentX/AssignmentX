from os import listdir
import subprocess


files = listdir('.')
files = [x for x in files if x[-4:] == '.svg']
#print files
for f in files:
    cmd = 'convert -density 500 -resize 256x256 ' + f + ' ' + f[:-4] + '.png'
    #subprocess.Popen(cmd)
    subprocess.check_output(['bash', '-c', cmd])
