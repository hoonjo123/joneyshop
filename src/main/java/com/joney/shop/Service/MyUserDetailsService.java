package com.joney.shop.Service;

import com.joney.shop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User(유저아이디, 비번, 권한) 해주세요
//        return new User(유저아이디, 비번, 권한) 해주세요   //유저가 제출한 네임과 페스워드 -> 유저디테일 서비스로 도착 -> 디비에서 꺼내주면 일치하는지 비교해봄 -> 자동으로 해싱해줌 => 비번확인되면쿠키 보내줌
//        입장권을 제출하면 로그인 여부 판단 가능
        // 스프링시큐리티가 자동으로 검증을 해주지만 스프링은어디에 비번이 저장되어있는지 모름 코딩 작성 필요
        var result = memberRepository.findByUsername(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("그런아이디없는데?");
//        user.setUsername();
//        user.getDisplayName();
        }
        var user = result.get();
//        List<GrantedAuthority> 권한 = new ArrayList<String>();
//        권한.add("");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));

        //특정아이디에 권한을 주고 싶다면? if문을 써서 해당 유저가 관리자임이라고 명시해주면 된다.

        CustomUser customUser = new CustomUser(user.getUsername(),user.getPassword(),authorities);
        customUser.displayName = user.getDisplayName();
        customUser.id = user.getId();

        return customUser;

//        return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }


