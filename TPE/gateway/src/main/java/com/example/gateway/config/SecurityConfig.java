package com.example.gateway.config;

import com.example.gateway.security.AuthorityConstant;
import com.example.gateway.security.jwt.JwtFilter;
import com.example.gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http ) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable );
        http
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http
                .securityMatcher("/api/**" )
                .authorizeHttpRequests( authz -> authz
                        //el orden va de más específica a menos específica
                        .requestMatchers( HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers( HttpMethod.POST, "/api/users/sign-in").permitAll() // es usuarios pero del gateway(para autenticacion)
                        //Administrador
                        .requestMatchers(HttpMethod.GET, "/administradores/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.PUT, "/administradores/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.POST, "/administradores/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.DELETE, "/administradores/**").hasAuthority(AuthorityConstant._ADMIN)

                        //Cuenta
                        .requestMatchers(HttpMethod.GET, "/cuentas/habilitadas").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/cuentas/deshabilitadas").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers( HttpMethod.GET, "/cuentas/**").permitAll() //cada uno puede acceder a su cuenta
                        .requestMatchers(HttpMethod.GET, "/cuentas").hasAuthority(AuthorityConstant._ADMIN) //Lista de cuentas

                        .requestMatchers(HttpMethod.PUT, "/cuentas/cobrarViaje").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/cuentas/habilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/cuentas/deshabilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/cuentas/**").permitAll() //editar cuenta

                        .requestMatchers(HttpMethod.POST, "/cuentas").permitAll()//save(crear cuenta)

                        .requestMatchers(HttpMethod.DELETE, "/cuentas/**").hasAuthority(AuthorityConstant._ADMIN)

                        //Mantenimiento
                        .requestMatchers(HttpMethod.GET, "/mantenimientos/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        .requestMatchers(HttpMethod.PUT, "/mantenimientos/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        .requestMatchers(HttpMethod.POST, "/mantenimientos/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        .requestMatchers(HttpMethod.DELETE, "/mantenimientos/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        //Monopatin
                        .requestMatchers(HttpMethod.GET, "/monopatines").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/monopatines/reportePorTiempoTotal").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers(HttpMethod.GET, "/monopatines/reportePorTiempoSinPausa").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers(HttpMethod.GET, "/monopatines/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        //.requestMatchers(HttpMethod.PUT, "/monopatines/reservarMonopatin/parada/{idParada}/monopatin/{idMonopatin}/reservar").hasAuthority(AuthorityConstant._ADMIN)
                        //.requestMatchers(HttpMethod.PUT, "/monopatines/{idMonopatin}/finalizarRecorrido").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/monopatines/habilitar").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers(HttpMethod.PUT, "/monopatines/deshabilitar").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers(HttpMethod.PUT, "/monopatines/pausa/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.POST, "/monopatines/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.DELETE, "/monopatines/**").hasAuthority(AuthorityConstant._ADMIN)

                        //Parada
                        .requestMatchers(HttpMethod.GET, "/paradas/habilitadas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paradas/deshabilitadas").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/paradas/cercanos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paradas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paradas").hasAuthority(AuthorityConstant._ADMIN)

                        //.requestMatchers(HttpMethod.PUT, "/{idParada}/monopatin/{idMonopatin}/quitarMonopatin").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/paradas/habilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/paradas/deshabilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/paradas/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.POST, "/paradas").hasAuthority(AuthorityConstant._ADMIN) //save

                        .requestMatchers(HttpMethod.DELETE, "/paradas/**").hasAuthority(AuthorityConstant._ADMIN)

                        //Usuario
                        .requestMatchers(HttpMethod.GET, "/usuarios/habilitados").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/usuarios/deshabilitados").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole(AuthorityConstant._ADMIN, AuthorityConstant._CLIENTE)
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.PUT, "/usuarios/reservarMonopatin").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/pausar").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/finalizarViaje").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/habilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/usuarios/deshabilitar/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.POST, "/usuarios").hasAuthority(AuthorityConstant._ADMIN) //save

                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAuthority(AuthorityConstant._ADMIN)

                        //Viaje
                        .requestMatchers(HttpMethod.GET, "/viajes").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.GET, "/viajes/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.PUT, "/viajes/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.POST, "/viajes").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.POST, "/viajes/**").hasAuthority(AuthorityConstant._ADMIN)

                        .requestMatchers(HttpMethod.DELETE, "/viajes/**").hasAuthority(AuthorityConstant._ADMIN)

                        .anyRequest().authenticated()
                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}
