package com.datn.dongho5s.Controller;

import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.NhanVienRepository;
import com.datn.dongho5s.Request.LoginAdminRequest;
import com.datn.dongho5s.Request.LoginRequest;
import com.datn.dongho5s.Security.AccountFilterService;
import com.datn.dongho5s.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/admin")
    public String viewHome(){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
        return "admin/index";
    }


    @GetMapping("/login-admin")
    public String viewLogin(){
        return "admin/login";
    }

     @GetMapping("/admin/logout")
     public String logout(){
         HttpSession session = request.getSession();
         session.removeAttribute("admin");
        return "redirect:/login-admin";
    }



    @PostMapping("/post/login")
    public ModelAndView authenticateUser(@Valid LoginAdminRequest loginAdminRequest, BindingResult bindingResult, Model model) throws Exception {
        // Xử lý đăng nhập và kiểm tra kết quả
//        Authentication authentication;
        HttpSession session = request.getSession();
        try {
//            authentication = authenticate(loginAdminRequest.getEmail(), loginAdminRequest.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            NhanVien userEntity = nhanVienRepository.getNhanVienByEmail(loginAdminRequest.getEmail());
            if(passwordEncoder.matches(loginAdminRequest.getPassword(),userEntity.getMatKhau())){
                session.setAttribute("admin",userEntity);
                ModelAndView mv = new ModelAndView("redirect:/admin");
                return mv;

            }else{
                model.addAttribute("error", "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu.");
                return new ModelAndView("admin/login");
            }
//            String jwt = tokenProvider.generateToken(authentication);
//            session.setAttribute("admin",userEntity);
//            System.out.println(session.getAttribute("admin")+"aaaaaaaaaaa");
            // Thực hiện chuyển hướng đến view "login-success.html" và truyền dữ liệu cần hiển thị
//            ModelAndView mv = new ModelAndView("redirect:/admin");
//            mv.addObject("id", userEntity.getId());
//            mv.addObject("description", "Đăng nhập thành công");
//            mv.addObject("name", userEntity.getEmail());
            // Truyền token dưới dạng hidden input trong form
//            mv.addObject("token", jwt);

        } catch (Exception ex) {
            System.out.println(ex);
            model.addAttribute("error", "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu.");
            // Trả về view login
            return new ModelAndView("admin/login");
        }
    }


    public Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
