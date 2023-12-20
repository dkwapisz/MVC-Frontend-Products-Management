package org.pk.lab4.service.http;

import org.pk.lab4.model.Product;
import org.pk.lab4.model.ProductSummary;
import org.pk.lab4.service.exception.NotFoundException;
import org.pk.lab4.service.exception.ServerErrorException;
import org.pk.lab4.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class HttpServiceImpl implements HttpService {

    // TODO Request status codes validation (onStatus method)

    private final WebClient webClient;
    private final String productBaseUrl;
    private final String productBaseParamUrl;

    public HttpServiceImpl(WebClient.Builder webClientBuilder, @Value("${backend.base.url}") String backendBaseUrl,
                           @Value("${product.base.url}") String productBaseUrl,
                           @Value("${product.base.param.url}") String productBaseParamUrl) {
        this.webClient = webClientBuilder.baseUrl(backendBaseUrl).build();
        this.productBaseUrl = productBaseUrl;
        this.productBaseParamUrl = productBaseParamUrl;
    }

    @Override
    public List<ProductSummary> getAllProducts() {
        return webClient.get()
                .uri(productBaseUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.equals(HttpStatus.INTERNAL_SERVER_ERROR),
                        clientResponse -> Mono.error(new NotFoundException())
                )
                .bodyToFlux(ProductSummary.class)
                .collectList()
                .block();
    }

    @Override
    public Product getProductDetails(String productId) {
        return webClient.get()
                .uri(productBaseParamUrl, productId)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.error(new NotFoundException());
                            } else {
                                return Mono.error(new ServerErrorException());
                            }
                        }
                )
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public Product createProduct(Product product) {
        return webClient.post()
                .uri(productBaseUrl)
                .bodyValue(product)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                return Mono.error(new ValidationException());
                            } else {
                                return Mono.error(new ServerErrorException());
                            }
                        }
                )
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        return webClient.patch()
                .uri(productBaseParamUrl, productId)
                .bodyValue(product)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.error(new NotFoundException());
                            } else if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                return Mono.error(new ValidationException());
                            } else {
                                return Mono.error(new ServerErrorException());
                            }
                        }
                )
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void deleteProduct(String productId) {
        webClient.delete()
                .uri(productBaseParamUrl, productId)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.error(new NotFoundException());
                            } else {
                                return Mono.error(new ServerErrorException());
                            }
                        }
                )
                .toBodilessEntity()
                .block();
    }
}
