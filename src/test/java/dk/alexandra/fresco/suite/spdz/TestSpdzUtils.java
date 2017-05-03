package dk.alexandra.fresco.suite.spdz;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import dk.alexandra.fresco.framework.value.OInt;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.field.integer.BasicNumericFactory;
import dk.alexandra.fresco.suite.spdz.utils.Util;

public class TestSpdzUtils {

  @Ignore
  @Test(expected = IllegalStateException.class)
  public void testUtilGetModulusNotSet(){
    Util.getModulus();
  }
  
  @Test
  public void testGetHashFunction() throws NoSuchAlgorithmException{
    Util util = new Util();
    util.getHashFunction();
    MessageDigest digest = util.getHashFunction();
    Assert.assertThat(digest.getAlgorithm(), Is.is(MessageDigest.getInstance("SHA-256").getAlgorithm()));
  }
  
  @Test
  public void testGetInputStream(){
    InputStream is = Util.getInputStream("src/test/resources/circuits/md5.txt");
    try {
      Assert.assertThat(is.available(), Is.is(1781599)); // Magicnumber relates to the file above
    } catch (IOException e) {
    }
  }
  
  @Test(expected = IllegalStateException.class)
  public void testGetInputStreamNoSuchresource(){
    Util.getInputStream("i-do-not-exist");
  }
  
  @Test
  public void testConstructPolynomial() {
    Util.setModulus(BigInteger.valueOf(7));
    BigInteger[] bigInts = Util.constructPolynomial(1, 1);
    Assert.assertThat(bigInts[0], Is.is(BigInteger.valueOf(6)));
    Assert.assertThat(bigInts[1], Is.is(BigInteger.valueOf(2)));
  }
  
  @Test
  public void testConstructPolynomial2() {
    Util.setModulus(BigInteger.valueOf(7));
    BigInteger[] bigInts = Util.constructPolynomial(1, 2);
    Assert.assertThat(bigInts[0], Is.is(BigInteger.valueOf(1)));
    Assert.assertThat(bigInts[1], Is.is(BigInteger.valueOf(6)));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testMakeOpenProtocolWrongSizes2D(){
    OInt[][] open = new OInt[4][2];
    SInt[][] closed = new SInt[2][4];
    Util.makeOpenProtocol(closed, open, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeOpenProtocolWrongSizes(){
    OInt[] open = new OInt[4];
    SInt[] closed = new SInt[2];
    Util.makeOpenProtocol(closed, open, null);
  }
  
  @Test
  public void testSIntFillRemaining() {
    BasicNumericFactory bnf = null;
    
  }
  
  @Test
  public void testZeroFill() {
    BigInteger[][] matrix = new BigInteger[5][5];
    Util.zeroFill(matrix);
    for(BigInteger[] row : matrix){
      for(BigInteger value: row){
        Assert.assertThat(value, Is.is(BigInteger.ZERO));
      }
    }
  }
  
  @Test
  public void testGetRandomNumber() {
    Util.setModulus(BigInteger.valueOf(7));
    Random rand = new Random(0);
    BigInteger random = Util.getRandomNumber(rand);
    Assert.assertThat(random, Is.is(BigInteger.valueOf(5)));
  }
  
}
