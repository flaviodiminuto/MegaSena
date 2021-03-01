package com.flavio.android.megasena.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flavio.android.megasena.Modelos.sorteio.ListaRateioPremio;
import com.flavio.android.megasena.Modelos.sorteio.MuniciipUFGanhadores;
import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class MunicipioGanhador {
    public Long id;
    public Long idSorteio;
    public Integer ganhadores;
    public String municipio;
    public String uf;
}

class Rateio {
    public Long id;
    public Long idSorteio;
    public Integer faixa;
    public Integer numeroDeGanhadores;
    public Double valorPremio;
}

class Dezena {
    public Long id;
    public Long idSorteio;
    public Integer numero_1;
    public Integer numero_2;
    public Integer numero_3;
    public Integer numero_4;
    public Integer numero_5;
    public Integer numero_6;
}

public class DaoUltimoSorteio extends DaoGeneralista {
    public Long id;
    private Integer numero;
    private String dataApuracao;
    private Double valorEstimadoProximoConcurso;
    private String dataProximoConcurso;
    public Integer numeroConcursoProximo;
    public List<MunicipioGanhador> listaMunicipioUFGanhadores;
    public List<Rateio> rateioList;
    public Dezena dezena;


    private final String tabelaSorteio = "sorteio";
    private final String tabelaRateio = "rateio";
    private final String tabelaMunicipio = "municipio_ganhador";
    private final String tabelaDezenas = "dezenas";

    Context context;

    public DaoUltimoSorteio(Context context){
        super(context);
        this.context = context;
        this.criarTabela ();
    }

    private void criarTabela(){
        criaTabelaSortieo();
        criarTabelaRateio();
        criarTabelaMunicipioGanhador();
        criarTabelaDezenas();
    }

    private void criarTabelaMunicipioGanhador() {
        String campos =" 'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'id_sorteio' INTEGER, " +
                "'ganhadores' INTEGER, " +
                "'municipio' REAL, " +
                "'uf' DOUBLE ";
        criarTabela ( this.tabelaMunicipio, campos);
    }

    private void criarTabelaRateio() {
        String campos =" 'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'id_sorteio' INTEGER, " +
                "'faixa' INTEGER, " +
                "'numero_de_ganhadores' INTEGER, " +
                "'valor_premio' REAL ";
        criarTabela ( this.tabelaRateio, campos);
    }

    private void criaTabelaSortieo() {
        String campos =" 'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'numero' INTEGER, " +
                "'data_apuracao' REAL, " +
                "'valor_estimado_proximo_concurso' DOUBLE, " +
                "'data_proximo_concurso' TEXT, " +
                "'numero_proximo_concurso' INTEGER ";
        criarTabela ( this.tabelaSorteio, campos);
    }



    private void criarTabelaDezenas() {
        String campos =" 'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'id_sorteio' INTEGER, " +
                "'numero_1' INTEGER, " +
                "'numero_2' INTEGER, " +
                "'numero_3' INTEGER, " +
                "'numero_4' INTEGER, " +
                "'numero_5' INTEGER, " +
                "'numero_6' INTEGER ";
        criarTabela ( this.tabelaDezenas, campos);
    }

    public void persistir(UltimoSorteioDTO sorteio){
        this.numero = sorteio.numero;
        this.dataApuracao = sorteio.dataApuracao;
        this.valorEstimadoProximoConcurso = sorteio.valorEstimadoProximoConcurso;
        this.dataProximoConcurso = sorteio.dataProximoConcurso;
        this.numeroConcursoProximo = sorteio.numeroConcursoProximo;
        this.listaMunicipioUFGanhadores = municipioDtoToDao(sorteio.listaMunicipioUFGanhadores);
        this.rateioList = rateioDtoToDao(sorteio.listaRateioPremio);
        this.dezena = listaDezenaToDao(sorteio.listaDezenas);
        this.persistir();
    }

    public List<MunicipioGanhador> municipioDtoToDao(List<MuniciipUFGanhadores> municipioDTOList){
        List<MunicipioGanhador> list = new ArrayList<>();
        for (MuniciipUFGanhadores municipioDTO : municipioDTOList ) {
            MunicipioGanhador municipioGanhador = new MunicipioGanhador();
            municipioGanhador.ganhadores = municipioDTO.ganhadores;
            municipioGanhador.municipio = municipioDTO.municipio;
            municipioGanhador.uf = municipioDTO.uf;
            list.add(municipioGanhador);
        }
        return list;
    }

    public List<Rateio> rateioDtoToDao(List<ListaRateioPremio> rateioDTOList){
        List<Rateio> list = new ArrayList<>();
        for (ListaRateioPremio rateioDto : rateioDTOList ) {
            Rateio rateio = new Rateio();
            rateio.faixa = rateioDto.faixa;
            rateio.numeroDeGanhadores = rateioDto.numeroDeGanhadores;
            rateio.valorPremio = rateioDto.valorPremio;
            list.add(rateio);
        }
        return list;
    }

    public Dezena listaDezenaToDao(List<String> dezenasList){
        Dezena dezena = new Dezena();
        int i = 0;
        dezena.numero_1 = Integer.parseInt(dezenasList.get(i++));
        dezena.numero_2 = Integer.parseInt(dezenasList.get(i++));
        dezena.numero_3 = Integer.parseInt(dezenasList.get(i++));
        dezena.numero_4 = Integer.parseInt(dezenasList.get(i++));
        dezena.numero_5 = Integer.parseInt(dezenasList.get(i++));
        dezena.numero_6 = Integer.parseInt(dezenasList.get(i));
        return dezena;
    }

    private void persistir(){
        deletar(this.tabelaSorteio,"");
        deletar(this.tabelaRateio,"");
        deletar(this.tabelaMunicipio,"");
        deletar(this.tabelaDezenas,"");

        long id = inserir(this.tabelaSorteio, sorteioToContentValue());
        if(id < 0 ) return;

        for(Rateio rateio: this.rateioList) {
            rateio.idSorteio = id;
            inserir(this.tabelaRateio, rateioToContentValue(rateio));
        }

        for(MunicipioGanhador municipio: this.listaMunicipioUFGanhadores) {
            municipio.idSorteio = id;
            inserir(this.tabelaMunicipio, municipioToContentValue(municipio));
        }

        this.dezena.idSorteio = id;
        inserir(this.tabelaDezenas, dezenaToContentValue(this.dezena));
    }

    public ContentValues sorteioToContentValue(){
        Map<String , Object> map = new HashMap<>();
        map.put("numero", this.numero);
        map.put("data_apuracao", this.dataApuracao);
        map.put("valor_estimado_proximo_concurso", this.valorEstimadoProximoConcurso);
        map.put("data_proximo_concurso", this.dataProximoConcurso);
        map.put("numero_proximo_concurso", this.numeroConcursoProximo);
        return toContentValue(map);
    }

    private ContentValues rateioToContentValue(Rateio rateio) {
        Map<String , Object> map = new HashMap<>();
        map.put("id_sorteio", rateio.idSorteio);
        map.put("faixa", rateio.faixa);
        map.put("numero_de_ganhadores", rateio.numeroDeGanhadores);
        map.put("valor_premio", rateio.valorPremio);
        return toContentValue(map);
    }

    private ContentValues municipioToContentValue(MunicipioGanhador municipio) {
        Map<String , Object> map = new HashMap<>();
        map.put("id_sorteio", municipio.idSorteio);
        map.put("ganhadores", municipio.ganhadores);
        map.put("municipio", municipio.municipio);
        map.put("uf", municipio.uf);
        return toContentValue(map);
    }

    private ContentValues dezenaToContentValue(Dezena dezena) {
        Map<String , Object> map = new HashMap<>();
        map.put("id_sorteio", dezena.idSorteio);
        map.put("numero_1", dezena.numero_1);
        map.put("numero_2", dezena.numero_2);
        map.put("numero_3", dezena.numero_3);
        map.put("numero_4", dezena.numero_4);
        map.put("numero_5", dezena.numero_5);
        map.put("numero_6", dezena.numero_6);
        return toContentValue(map);
    }

    public UltimoSorteioDTO buscarUltimoSorteio() {
        Cursor cursorSorteio = consulta ( "SELECT * FROM "+ tabelaSorteio );
        Cursor cursorRateio = consulta ( "SELECT * FROM "+ tabelaRateio );
        Cursor cursorMunicipio = consulta ( "SELECT * FROM "+ tabelaMunicipio );
        Cursor cursorDezena = consulta ( "SELECT * FROM "+ tabelaDezenas );

        getSorteio(cursorSorteio);
        if(this.id == null) return null;
        getRateio(cursorRateio);
        getMunicipio(cursorMunicipio);
        dezenaStr(cursorDezena);

        return mapToUltimoSorteio();
    }

    public void getSorteio(Cursor cursor){
        if(cursor.moveToFirst ()){
            int i = 0;
            this.id = cursor.getLong(i++);
            this.numero = cursor.getInt(i++);
            this.dataApuracao = cursor.getString(i++);
            this.valorEstimadoProximoConcurso = cursor.getDouble(i++);
            this.dataProximoConcurso = cursor.getString(i++);
            this.numeroConcursoProximo = cursor.getInt(i);
        }
    }

    public void getRateio(Cursor cursor){
        List<Rateio> list = new ArrayList<>();
        if(cursor.moveToFirst ()){
            do{
                int i = 0;
                Rateio rateio = new Rateio();
                rateio.id = cursor.getLong(i++);
                rateio.idSorteio = cursor.getLong(i++);
                rateio.faixa = cursor.getInt(i++);
                rateio.numeroDeGanhadores = cursor.getInt(i++);
                rateio.valorPremio = cursor.getDouble(i);
                list.add ( rateio);
            }while(cursor.moveToNext ());
        }
        this.rateioList = list;
    }

    public void getMunicipio(Cursor cursor){
        List<MunicipioGanhador> list = new ArrayList<>();
        if(cursor.moveToFirst ()){
            do {
                int i = 0;
                MunicipioGanhador municipio = new MunicipioGanhador();
                municipio.id = cursor.getLong(i++);
                municipio.idSorteio = cursor.getLong(i++);
                municipio.ganhadores = cursor.getInt(i++);
                municipio.municipio = cursor.getString(i++);
                municipio.uf = cursor.getString(i);
                list.add ( municipio);
            }while(cursor.moveToNext ());
        }
        this.listaMunicipioUFGanhadores = list;
    }

    public void dezenaStr(Cursor cursor){
        if(cursor.moveToFirst ()){
            Dezena dezena = new Dezena();
            int i = 0;
            dezena.id = cursor.getLong(i++);
            dezena.idSorteio = cursor.getLong(i++);
            dezena.numero_1 = cursor.getInt(i++);
            dezena.numero_2 = cursor.getInt(i++);
            dezena.numero_3 = cursor.getInt(i++);
            dezena.numero_4 = cursor.getInt(i++);
            dezena.numero_5 = cursor.getInt(i++);
            dezena.numero_6 = cursor.getInt(i);
            this.dezena = dezena;
        }
    }

    public UltimoSorteioDTO mapToUltimoSorteio() {
        UltimoSorteioDTO sorteio = new UltimoSorteioDTO();
        List<MuniciipUFGanhadores> listaMunicipioUFGanhadores = municipiosToDTO();
        List<ListaRateioPremio> listaRateioPremio = rateioToDTO();
        List<String> listaDezenas = dezenasToDTO();

        sorteio.id = this.id;
        sorteio.numero = this.numero;
        sorteio.dataApuracao = this.dataApuracao;
        sorteio.dataProximoConcurso = this.dataProximoConcurso;
        sorteio.numeroConcursoProximo = this.numeroConcursoProximo;
        sorteio.listaMunicipioUFGanhadores = listaMunicipioUFGanhadores;
        sorteio.listaRateioPremio = listaRateioPremio;
        sorteio.listaDezenas = listaDezenas;

        return sorteio;
    }

    public List<MuniciipUFGanhadores>  municipiosToDTO(){
        List<MuniciipUFGanhadores> listaRateioPremio = new ArrayList<>();
        for(MunicipioGanhador municipio : this.listaMunicipioUFGanhadores){
            MuniciipUFGanhadores municipioDto = new MuniciipUFGanhadores();
            municipioDto.ganhadores = municipio.ganhadores;
            municipioDto.municipio = municipio.municipio;
            municipioDto.uf = municipio.uf;
            municipioDto.nomeFatansiaUL = "";
            municipioDto.serie = "";
            listaRateioPremio.add(municipioDto);
        }
        return listaRateioPremio;
    }

    public List<ListaRateioPremio> rateioToDTO(){
        List<ListaRateioPremio> listaRateioPremio = new ArrayList<>();
        for(Rateio rateio : this.rateioList){
            ListaRateioPremio rateioDto = new ListaRateioPremio();
            rateioDto.faixa = rateio.faixa;
            rateioDto.numeroDeGanhadores = rateio.numeroDeGanhadores;
            rateioDto.valorPremio = rateio.valorPremio;
            rateioDto.descricaoFaixa = "";
            listaRateioPremio.add(rateioDto);
        }
        return listaRateioPremio;
    }

    public List<String>  dezenasToDTO(){
        List<String> listaDezenasStr = new ArrayList<>();
        if(dezena == null) return listaDezenasStr;
        listaDezenasStr.add(dezenaStr(dezena.numero_1));
        listaDezenasStr.add(dezenaStr(dezena.numero_2));
        listaDezenasStr.add(dezenaStr(dezena.numero_3));
        listaDezenasStr.add(dezenaStr(dezena.numero_4));
        listaDezenasStr.add(dezenaStr(dezena.numero_5));
        listaDezenasStr.add(dezenaStr(dezena.numero_6));

        return listaDezenasStr;
    }

    public String dezenaStr(Integer numero){
        if(numero < 10)
            return "00"+ numero;
        else
            return "0"+numero;
    }
}
