<html>
    <head>
<!--        <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="styles/styles.css">


        <script src="/webjars/sockjs-client/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/stomp.min.js"></script>
        <script src="/webjars/jquery/jquery.min.js"></script>
        <script>
                   //Web Sockets
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var userName = "nyla";

        stompClient.subscribe('/topic/customerPromotions/'+userName, function (promotionResponse) {

                var promotion = JSON.parse(promotionResponse.body);
                addPromotion(promotion);
            });
    });

    function urlify(text) {
        var urlRegex = /(https?:\/\/[^\s]+)/g;
        return text.replace(urlRegex, function(url) {
        return '<a href="' + url + '">' + url + '</a>';
        })
    }

    function addPromotion(promotion)
    {
       var promotionsPanel = document.getElementById("promotionsPanel");

       if(promotion == null || promotion.products.length == 0)
        {
            promotionsPanel.style = "display: none";
            return;
        }
        else
        {
            promotionsPanel.style = "display: block";
        }

        var promotionContent = document.getElementById("promotions");
        var promotionHTML = "<p>Based on your previous order, you may also be interested in the following:</p>";
         var product = {};

         promotionHTML += "<table id='dataRows'>";
         promotionHTML += "<tr><th>Product</th></tr>";

         for (let x in promotion.products) {
            product = promotion.products[x];

            promotionHTML +="<tr><td>"+product.name+"</td></tr>";
         }

         promotionHTML += "</table>";

         promotionContent.innerHTML = promotionHTML;

        }
        </script>
    </head>

    <body>

    <h1>Retail Application Demo</h1>
     <h4>Hello, ${customerId}</h4>
        <ul>
            <li><a href="/swagger-ui.html">Swagger-UI</a></li>
        </ul>

        <div id="promotionsPanel" style="display: none">
            <hr/>

            <h2>Promotions</h2>

            <div class="promotions-container" id="promotions">
            </div>
        </div>


        <div id="favoritesPanel" style="display: none">
            <hr/>
            <h2>Favorites</h2>
            <p>These appear to be your favorite</p>
            <div id="dataRows">
                <table >
                    <tr><th>Product</th></tr>
                    <tbody id="customerFavorites">
                    </tbody>
                </table>
            </div>
        </div>



    <script>

    $(document).ready(
    function() {
        var sse = new EventSource('customer/favorites/favorite/nyla');

        sse.onmessage = function(message) {

        console.log("data: "+message.data);
       	var customerFavorites= JSON.parse(message.data);
       	var tableContent = document.getElementById("customerFavorites");
       	tableContent.innerHTML = ""; //TODO: should optimize this to not redraw everytime

        var productQuantity;

        if(customerFavorites != null && customerFavorites.favorites.length > 0 )
        {
           //show panel
           var favoritesPanel = document.getElementById("favoritesPanel");
           favoritesPanel.style  = "display: block";
        }


        for (let x in customerFavorites.favorites) {
           productQuantity = customerFavorites.favorites[x];

            if(tableContent.innerHTML.indexOf(productQuantity.product.name) > -1)
        	    continue; //already exist

            $('#customerFavorites')
            .prepend('<tr><td>'+ productQuantity.product.name + '</td></tr>');
        }


       }
   }
);
</script>
    </body>
</html>