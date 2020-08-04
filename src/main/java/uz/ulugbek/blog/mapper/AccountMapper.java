package uz.ulugbek.blog.mapper;

import org.mapstruct.Mapper;
import uz.ulugbek.blog.domain.Account;
import uz.ulugbek.blog.dto.AccountDto;

@Mapper(componentModel = "spring", uses = {})
public interface AccountMapper  extends EntityMapper<AccountDto, Account>{

    default Account fromId(Long id) {
        if (id == null) {
            return null;
        }
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
