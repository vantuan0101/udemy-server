package com.java.udemy.service.concretions;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
  private final Cloudinary cloudinary;

  public Map<String, Object> upload(MultipartFile file) {
    try {
      Map<String, Object> data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
          "resource_type", "auto"));
      return data;
    } catch (IOException io) {
      throw new RuntimeException("Image upload fail");
    }
  }
}
