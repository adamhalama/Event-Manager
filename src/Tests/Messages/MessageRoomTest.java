package Tests.Messages;

import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MessageRoomTest
{

  private MessageRoom messageRoom;
  /*
    -------- Chunk 2
    - Message 11
    ...
    - Message 6

    -------- Chunk 1
    - Message 5
    ...
    - Message 1
  */
  private ArrayList<Message> messagesChunk1 = new ArrayList<>(Arrays.asList(
      new Message(1, 1620840000, "Message 5"),
      new Message(2, 1620830000, "Message 4"),
      new Message(3, 1620820000, "Message 3"),
      new Message(4, 1620810000, "Message 2"),
      new Message(5, 1620800000, "Message 1")
  ));
  private ArrayList<Message> messagesChunk2 = new ArrayList<>(Arrays.asList(
      new Message(5, 1620900000, "Message 11"),
      new Message(1, 1620890000, "Message 10"),
      new Message(2, 1620880000, "Message 9"),
      new Message(3, 1620870000, "Message 8"),
      new Message(4, 1620860000, "Message 7"),
      new Message(5, 1620850000, "Message 6")
  ));
  private ArrayList<Integer> users = new ArrayList<>(Arrays.asList(1,2,3,4,5));

  @BeforeEach void setUp()
  {
    this.messageRoom = new MessageRoom(1, "Message Room", this.users, this.messagesChunk1);
  }

  @Test void setName()
  {
    assertEquals("Message Room", this.messageRoom.getName());
    this.messageRoom.setName("Message Room 2");
    assertEquals("Message Room 2", this.messageRoom.getName());
  }

  @Test void setUsersIDs()
  {
    System.out.println(this.messageRoom.getUsersIDs());
    assertEquals(5, this.messageRoom.getUsersIDs().size());
    ArrayList<Integer> users = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
    this.messageRoom.setUsersIDs(users);
    assertEquals(6, this.messageRoom.getUsersIDs().size());
  }

  @Test void setMessages()
  {
    assertEquals(5, this.messageRoom.getMessages().size());
    this.messageRoom.setMessages(this.messagesChunk2);
    assertEquals(6, this.messageRoom.getMessages().size());
    assertEquals("Message 11", this.messageRoom.getMessages().get(0).getMessage());
  }

  @Test void addMessages()
  {
    assertEquals(5, this.messageRoom.getMessages().size());
    this.messageRoom.addMessages(this.messagesChunk2);
    assertEquals(11, this.messageRoom.getMessages().size());
    assertEquals("Message 11", this.messageRoom.getMessages().get(0).getMessage());
  }

  @Test void addMessage()
  {
    assertEquals(5, this.messageRoom.getMessages().size());
    this.messageRoom.addMessage(this.messagesChunk2.get(0));
    assertEquals(6, this.messageRoom.getMessages().size());
    assertEquals("Message 11", this.messageRoom.getMessages().get(5).getMessage());
  }

  @Test void setAllMessagesLoaded()
  {
    assertEquals(true, this.messageRoom.getAllMessagesLoaded());
    this.messageRoom.setAllMessagesLoaded(false);
    assertEquals(false, this.messageRoom.getAllMessagesLoaded());
  }
}