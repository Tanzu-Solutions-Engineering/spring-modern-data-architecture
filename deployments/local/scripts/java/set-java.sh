#!/bin/sh

# UTIL
script_name="set-java.sh"
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;36m'
BOLD='\033[1m'
UNDERLINE='\033[4m'
END_FORMAT=''


version=$1
if [[ $version == "" ]] || [[ $version == "-h" ]] ||[[ $version == "--help" ]];
then
    echo "${BLUE}The ${BOLD}${script_name}${END_FORMAT}${BLUE} script was generated for the 2022 \nSpring One Conference Modern Data Architecture \nwith Spring workshop.\n"
    echo "The purpose of this script is to update your Java Version by \nmodifying the JAVA_HOME environment variable. This is a local \nchange and does not modify your .bash_profile or .zshrc files."
    echo "This script is for ${BOLD}Mac Only${END_FORMAT}${BLUE}."
    echo "\n${UNDERLINE}Use Instructions:${END_FORMAT}${BLUE}"

    echo "Provide a Java version. For example, \n\n${BOLD}$script_name 11${END_FORMAT}\n"
    exit 0
fi

pattern="*.jdk/*"
installed_versions=($(echo $(/usr/libexec/java_home --xml | grep .jdk/)))

installed_version_numbers=()
num_installed_versions=${#installed_versions[@]}

version_is_installed=0

for (( i=0; i < ${num_installed_versions}; i++ ))
do
    temp=${installed_versions[$i]}
    temp=${temp#*/Library/Java/JavaVirtualMachines/}
    temp=${temp%/Contents/Home*}

    version_num=${temp#*-}
    version_num=${version_num%.jdk*}
    installed_version_numbers+=$version_num

    installed_versions[$i]="$temp ($version_num)"

    if [ $version_num == $version ];
    then version_is_installed=1
    fi
done

if [ $version_is_installed == 1 ];
then
    echo "${GREEN}${BOLD}Java version '$version' is installed!\n${END_FORMAT}${GREEN}"

    export JAVA_HOME=$(/usr/libexec/java_home -v $version)
    echo "${GREEN}${UNDERLINE}Updated Java Home\n${END_FORMAT}${GREEN}JAVA_HOME='$JAVA_HOME'\n"
    echo "${UNDERLINE}Updated Java Version\n${END_FORMAT}${GREEN}$(java --version)"
    echo ""
else
    echo "${RED}${BOLD}Java version '$version' is not installed!\n${END_FORMAT}${RED}"
    echo "Install Java version $version or try again with the \nversion number of a Java version that is \nalready installed on your computer."
    echo "${UNDERLINE}\nInstalled Java Versions${END_FORMAT}${RED}"
    printf "%s\n" "${installed_versions[@]}"
    echo ""
fi
