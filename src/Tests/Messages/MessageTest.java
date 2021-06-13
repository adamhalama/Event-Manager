package Tests.Messages;

import Shared.Messages.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest
{

  private Message message;

  @BeforeEach void setUp()
  {
    this.message = new Message(1, 1620855239, "Hello World!");
  }

  @Test void getUserID()
  {
    assertEquals(1, this.message.getUserID());
  }

  @Test void getTimestamp()
  {
    assertEquals(1620855239, this.message.getTimestamp());
  }

  @Test void getMessage()
  {
    assertEquals("Hello World!", this.message.getMessage());
  }
}