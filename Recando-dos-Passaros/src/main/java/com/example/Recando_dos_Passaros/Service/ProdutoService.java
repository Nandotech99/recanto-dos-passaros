package com.example.Recando_dos_Passaros.Service;

import com.example.Recando_dos_Passaros.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Model.Produto;
import com.example.Recando_dos_Passaros.Repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {
    private ProdutoRepository produtoRepository;

@Transactional
    public Produto saveProd(ProdutoDto produtoDto){
        Produto prod=new Produto();
        prod.setId(produtoDto.id());
        prod.setNome(produtoDto.nome());
        prod.setQuantidade(produtoDto.quantidade());
        prod.setQuantidadeMinima(produtoDto.quantidadeMinima());
        prod.setPreco(produtoDto.preco());
        prod.setRegistro(produtoDto.registro());

        return produtoRepository.save(prod);

    }

}
