package uz.ulugbek.blog.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.ulugbek.blog.domain.Account;
import uz.ulugbek.blog.dto.AccountDto;
import uz.ulugbek.blog.mapper.AccountMapper;
import uz.ulugbek.blog.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public JwtUserDetailsService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("ulugbek".equals(username)) {
            return new User(username, "$2y$12$oJph8cUPPcrt/edOJ.FfVO8ElrSw/vWiV2Xh9Q6v.nsvmDgc.JQmO",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Cacheable("currentUserAccount")
    public Account findByUsername(String username){
        return accountRepository.findByUsernameAndActive(username, 1).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<AccountDto> findByUsernameToDto(String username){
        return accountRepository.findByUsernameAndActive(username, 1).map(accountMapper::toDto);
    }

    public Boolean saveDto(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        accountRepository.save(account);
        return true;
    }

    public Boolean save(Account account) {
        accountRepository.save(account);
        return true;
    }

}
