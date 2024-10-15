package com.shopping.himanshu.service.image;

import com.shopping.himanshu.dto.ImageDto;
import com.shopping.himanshu.exceptions.ProductNotFound;
import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Image;
import com.shopping.himanshu.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageServiceInt {
    Image getImageById(Long id) throws ResourceNotFound;
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId) throws ProductNotFound, ResourceNotFound;
    void updateImage(MultipartFile file, Long imageId) throws ResourceNotFound;


}
