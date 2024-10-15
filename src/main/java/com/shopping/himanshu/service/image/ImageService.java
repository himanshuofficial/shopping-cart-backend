package com.shopping.himanshu.service.image;

import com.shopping.himanshu.dto.ImageDto;
import com.shopping.himanshu.exceptions.ProductNotFound;
import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Image;
import com.shopping.himanshu.model.Product;
import com.shopping.himanshu.repository.ImageRepository;
import com.shopping.himanshu.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceInt{

    private final ProductService productService;
    private final ImageRepository imageRepo;


    @Override
    public Image getImageById(Long id) throws ResourceNotFound {
        return imageRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Image not found with id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete, () -> {
            try {
                throw new ResourceNotFound("Image not found with id " + id);
            } catch (ResourceNotFound e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) throws ResourceNotFound {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file: files) {
            try {
                Image image = new Image();
                image.setName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/image/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepo.save(image);
                savedImage.setDownloadUrl("/api/v1/images/image/download/" + savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageName(savedImage.getName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDto.setImageId(savedImage.getId());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) throws ResourceNotFound {
        Image image = getImageById(imageId);
        try {
            image.setName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
