package com.example.Recando_dos_Passaros.Pck_Model;


public class Model {

    // TABELA - PRODUTO
    private int codigo;
    private String nome;
    private int quantidade;
    private int quantidade_min;
    private double preco;
    private String data;
    private String registro;

    // TABELA - VENDA
    private int cod_venda;
    private double valor_total;

    //TABELA - ITEM
    private int cod_item;
    private int cod_vendaFK;
    private int cod_produtoFK;
    private String produtoFK;
    private double precoFK;
    private int quantidadeFK;


        /*
        A MODEL CRIA OS SET'S E GET'S PARA MANIPULAR OS DADOS
        SE QUISERMOS DEFINI-LOS(SET) OU PEGA-LAS E USA-LOS(GET)


        SET's - Tem void, ou seja, não retorna o valor
                apenas o guarda na variavel

        GET's - Tem o tipo da variavel, porque
                retornam o valor, ou seja, eles devolvem
                o valor do set
         */

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        if (!nome.trim().isEmpty() && nome != null) {
            this.nome = nome;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade_min(int quantidade_min) {
        if (quantidade_min > 0) {
            this.quantidade_min = quantidade_min;
        }
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        }
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade_min() {
        return quantidade_min;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getRegistro() {
        return registro;
    }

    public void setCod_venda(int cod_venda) {
        if (cod_venda > 0) {
            this.cod_venda = cod_venda;
        }
    }

    public int getCod_venda() {
        return cod_venda;
    }

    public void setValor_total(double valor_total) {
        if (valor_total > 0) {
            this.valor_total = valor_total;
        }
    }

    public double getValor_total() {
        return valor_total;
    }


    public void setCod_item(int cod_item) {
        if (cod_item > 0) {
            this.cod_item = cod_item;
        }
    }

    public int getCod_item() {
        return cod_item;
    }

    public void setCod_vendaFK(int cod_vendaFK) {
        if (cod_vendaFK > 0) {
            this.cod_vendaFK = cod_vendaFK;
        }
    }

    public int getCod_vendaFK() {
        return cod_vendaFK;
    }

    public void setCod_produtoFK(int cod_produtoFK) {
        if (cod_produtoFK > 0) {
            this.cod_produtoFK = cod_produtoFK;
        }
    }

    public int getCod_produtoFK() {
        return cod_produtoFK;
    }

    public void setProdutoFK(String produtoFK) {
        if (!produtoFK.isEmpty() && produtoFK != null) {
            this.produtoFK = produtoFK;
        }
    }

    public String getProdutoFK() {
        return produtoFK;
    }

    public void setPrecoFK(double precoFK) {
        if (precoFK > 0) {
            this.precoFK = precoFK;
        }

    }

    public double getPrecoFK() {
        return precoFK;
    }

    public void setQuantidadeFK(int quantidadeFK) {
        if (quantidadeFK > 0) {
            this.quantidadeFK = quantidadeFK;

        }
    }

    public int getQuantidadeFK() {
        return quantidadeFK;
    }


}


