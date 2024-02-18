package services

import com.rabbitmq.client.{CancelCallback, Channel, Connection, ConnectionFactory, DefaultConsumer, DeliverCallback}

object RabbitMQService {

  private var rabbitMqConnection: Option[Connection] = None
  private var rabbitMqChannel: Option[Channel] = None

  def getRabbitMqConnection = rabbitMqConnection.getOrElse({
    val connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername("guest")
    connectionFactory.setPassword("guest")
    connectionFactory.setVirtualHost("/")
    connectionFactory.setHost("rabbit-server")
    connectionFactory.setPort(5672)
    val conn = connectionFactory.newConnection()
    rabbitMqConnection = Some(conn)
    conn
  })

  def getRabbitMqChannel = rabbitMqChannel.getOrElse({
    val channel = getRabbitMqConnection.createChannel()
    channel.exchangeDeclare("sensor-exchange", "direct", true)
    channel.queueDeclare("sensor-queue", true, false, false, null)
    channel.queueBind("sensor-queue", "sensor-exchange", "sensor-routing-key")
    rabbitMqChannel = Some(channel)
    channel
  })

  def sendMessage(message: String) = {
    getRabbitMqChannel.basicPublish("sensor-exchange", "sensor-routing-key", null, message.getBytes)
  }

  def receiveMessages(callback: DeliverCallback) = {
    val cancel: CancelCallback = consumerTag => {}
    getRabbitMqChannel.basicConsume("sensor-queue", true, callback, cancel)
  }

}

