package com.testtask.booking_system.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.apache.commons.lang3.StringUtils.trim;

@Component
@Slf4j
public class AccessLogFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    long start = System.nanoTime();
    try {
      chain.doFilter(req, res);
    } finally {
      long ms = (System.nanoTime() - start) / 1_000_000;
      log.info("{} {} -> {} ({} ms) User-Agent = \"{}\" IP = {}",
          req.getMethod(), req.getRequestURI(), res.getStatus(), ms,
          trim(req.getHeader("User-Agent")),
          req.getRemoteAddr());
    }
  }
}
