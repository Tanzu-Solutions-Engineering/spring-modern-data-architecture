brew install openjdk@17

sudo ln -sfn /usr/local/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
printf "\n"

echo exporting "JAVA_HOME=\$(/usr/libexec/java_home -v 17)"
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

echo "Java Version 17 Installed"
printf "\n"
java --version

