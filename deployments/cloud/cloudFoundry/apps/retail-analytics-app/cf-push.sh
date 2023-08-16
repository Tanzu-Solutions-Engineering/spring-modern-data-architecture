#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# retail-analytics-app
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app.yaml -p applications/retail-analytics-app/target/retail-analytics-app-0.0.3-SNAPSHOT.jar

