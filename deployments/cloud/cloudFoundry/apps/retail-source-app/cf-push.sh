#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# retail-source-app
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml -p applications/retail-source-app/target/retail-source-app-0.1.0-SNAPSHOT.jar

