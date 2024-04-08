#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

cd $GEMFIRE_HOME/bin

$GEMFIRE_HOME/bin/gfsh -e "start locator --name=localhost --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1"

$GEMFIRE_HOME/bin/gfsh -e "connect" - "configure pdx --read-serialized=true --disk-store"

$GEMFIRE_HOME/bin/gfsh -e "start server --name=server1 --server-bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --jmx-manager-hostname-for-clients=127.0.0.1 --bind-address=127.0.0.1 --http-service-bind-address=127.0.0.1 --locators=127.0.0.1[10334]"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=CustomerFavorites --type=PARTITION"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=Product --type=PARTITION"

$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=Promotion --type=PARTITION"