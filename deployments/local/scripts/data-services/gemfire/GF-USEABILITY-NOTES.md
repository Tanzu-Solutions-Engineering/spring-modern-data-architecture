127.0.0.1:6379> CLIENT LIST
ERR Unknown subcommand or wrong number of arguments for 'LIST'. Supported subcommands are: [SETNAME, GETNAME]




java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.vmware.retail.domain.Promotion]
at org.springframework.core.serializer.DefaultSerializer.serialize(DefaultSerializer


JSON has large payloads


body:[B@5f746944 channel:{"@class":"com.vmware.retail.domain.Promotion","id":"string","products":["java.util.ArrayList",[{"@class":"com.vmware.retail.domain.Product","id":"string","name":"string"}]]}
