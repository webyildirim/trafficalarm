package trafficalarm.core.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.accmee.trafficalarm.core.models.entities.Account;
import com.accmee.trafficalarm.core.repositories.AccountRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by webyildirim.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-config.xml")
public class AccountRepoTest {

    @Autowired
    private AccountRepo repo;

    private Account account;

    @Before
    @Transactional
    @Rollback(false)
    public void setup()
    {
        account = new Account();
        account.setName("name");
        account.setPassword("password");
        try {
			repo.createAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    @Transactional
    public void testFind()
    {
        Account account = repo.findAccount(this.account.getId());
        assertNotNull(account);
        assertEquals(account.getName(), "name");
        assertEquals(account.getPassword(), "password");
    }
}
