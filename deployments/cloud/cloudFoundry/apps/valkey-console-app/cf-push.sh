#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# valkey-console-app
cf push valkey-console-app -f deployments/cloud/cloudFoundry/apps/valkey-console-app/valkey-console-app.yaml -p applications/valkey-console-app/target/valkey-console-app-0.0.1-SNAPSHOT.jar
