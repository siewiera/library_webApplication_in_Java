package pl.siewiera.library;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.siewiera.library.repository_Reader.ReaderRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ReaderRepo readerRepo;

    public UserDetailsServiceImpl(ReaderRepo readerRepo) {
        this.readerRepo = readerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return readerRepo.findByEmail(s);
    }
}
