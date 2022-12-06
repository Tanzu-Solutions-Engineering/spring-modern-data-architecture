#!/bin/bash
GLOSSARY_INPUT_FILE="$HOME/templates/workshop/data/XX-glossary.csv"
GLOSSARY_OUTPUT_FILE="/opt/workshop/content/exercises/XX-glossary.md"
IFS=","

if [ ! -f $GLOSSARY_INPUT_FILE ]; then 
    echo "(Error) Input File Not Found: $GLOSSARY_INPUT_FILE"
elif [ ! -f $GLOSSARY_OUTPUT_FILE ]; then    
    echo "(Error) Output File Not Found: $GLOSSARY_OUTPUT_FILE"
else
    echo > $GLOSSARY_OUTPUT_FILE # Empty current .md file
    echo "This document provides defintions for the keywords we use throughout this workshop." >> $GLOSSARY_OUTPUT_FILE
    echo >> $GLOSSARY_OUTPUT_FILE

    while read type word definition
    do
        type=$(echo "$type" | tr "[:upper:]" "[:lower:]")

        if [[ "$type" == "sec" ]]; then
            echo >> $GLOSSARY_OUTPUT_FILE
            echo "## $word" >> $GLOSSARY_OUTPUT_FILE
            echo "$definition" >> $GLOSSARY_OUTPUT_FILE
            echo >> $GLOSSARY_OUTPUT_FILE
        else
            echo "**$word** - definition" >> $GLOSSARY_OUTPUT_FILE
            echo >> $GLOSSARY_OUTPUT_FILE
        fi
    done < <(tail -n +2 $GLOSSARY_INPUT_FILE)

    echo "Success! Glossary completed from $GLOSSARY_INPUT_FILE into $GLOSSARY_OUTPUT_FILE!"
fi