package question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

public class question1_2  extends junit.framework.TestCase
{
    question1.Ensemble<Integer> e1, e2;

    @BeforeEach
    public void setUp()
    {
        e1 = new question1.Ensemble<Integer>();
        e2 = new question1.Ensemble<Integer>();
    }

    @AfterEach
    public void tearDown()
    {
        e1 = null;
        e2 = null;
    }
    
    public void test_add(){
        assertEquals(true, e1.add(1));
        assertEquals(true, e1.add(2));
    }
    
    public void test_size(){
        e1.add(1);
        e1.add(2);
        assertEquals(2, e1.size());
    }
    
    public void test_union(){
        e1.add(1);
        e1.add(2);
        e1.add(3);
        
        e2.add(1);
        e2.add(2);
        e2.add(3);
        e2.add(4);
        
        assertEquals("[1, 2, 3, 4]",e1.union(e2).toString());
    }
    
    public void test_intersection(){
        e1.add(1);
        e1.add(2);
        e1.add(3);
        
        e2.add(1);
        e2.add(2);
        e2.add(3);
        e2.add(4);
        
        assertEquals("[1, 2, 3]",e1.inter(e2).toString());
    }
    
    public void test_difference(){
        e1.add(1);
        e1.add(2);
        e1.add(3);
        
        e2.add(1);
        e2.add(2);
        e2.add(3);
        e2.add(4);
        
        assertEquals("[]",e1.diff(e2).toString());
    }
    
    public void test_differenceSymetrique(){
        e1.add(1);
        e1.add(2);
        e1.add(3);
        
        e2.add(1);
        e2.add(2);
        e2.add(3);
        e2.add(4);
        
        assertEquals("[4]",e1.diffSym(e2).toString());
    }
}