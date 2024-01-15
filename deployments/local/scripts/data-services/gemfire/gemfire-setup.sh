#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

export GEMFIRE_HOME=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-10.0.2
cd $GEMFIRE_HOME/bin

$GEMFIRE_HOME/bin/gfsh -e "start locator --name=locator"

$GEMFIRE_HOME/bin/gfsh -e "connect" - "configure pdx --read-serialized=true --disk-store"

$GEMFIRE_HOME/bin/gfsh -e "start server --name=server --locators=localhost[10334]"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=CustomerFavorites --type=PARTITION"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=Product --type=PARTITION"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=Promotion --type=PARTITION"