package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @Before
  public void init(){
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(mockPrimary.getTorpedoCount())).thenReturn(true);
    when(mockSecondary.fire(mockSecondary.getTorpedoCount())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Success2(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success2(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary).fire(mockPrimary.getTorpedoCount());
    verify(mockSecondary).fire(mockSecondary.getTorpedoCount());
  }

  @Test
  public void primaryFired(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void secondaryFired(){
    // Arrange
    when(mockSecondary.fire(1)).thenReturn(true);


    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void bothEntry(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(false);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result1);
    assertEquals(false, result2);

  }

  @Test
  public void fireOnlySec(){
    // Arrange
    when(mockPrimary.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, never()).fire(mockPrimary.getTorpedoCount());
    verify(mockSecondary).fire(mockSecondary.getTorpedoCount());
  }

  @Test
  public void fireOnlyPrimary(){
    // Arrange
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockSecondary, never()).fire(mockSecondary.getTorpedoCount());
    verify(mockPrimary).fire(mockPrimary.getTorpedoCount());
  }


}
