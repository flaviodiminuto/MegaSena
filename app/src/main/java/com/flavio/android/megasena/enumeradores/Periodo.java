package com.flavio.android.megasena.enumeradores;

import com.flavio.android.megasena.util.DataUtil;

import java.util.Date;

public enum  Periodo {
    MES_ATUAL{
        @Override
        public String dataString() {
            long milisegundosData = System.currentTimeMillis() - mesMilisegundos;
            Date data = new Date(milisegundosData);
            return DataUtil.toString(data);
        }

        @Override
        public int quantidadeSorteios() {
            return quantidadeMensal;
        }
    },
    ULTIMO_TRIMESTRE {
        @Override
        public String dataString() {
            long milisegundosData = System.currentTimeMillis() - trimestreMilisegundos;
            Date data = new Date(milisegundosData);
            return DataUtil.toString(data);
        }

        @Override
        public int quantidadeSorteios() {
            return quantidadeMensal * 3;
        }
    },
    ULTIMO_SEMESTRE {
        @Override
        public String dataString() {
            long milisegundosData = System.currentTimeMillis() - semestreMilisegundos;
            Date data = new Date(milisegundosData);
            return DataUtil.toString(data);
        }

        @Override
        public int quantidadeSorteios() {
            return quantidadeMensal * 6;
        }
    },
    ULTIMO_ANO {
        @Override
        public String dataString() {
            long milisegundosData = System.currentTimeMillis() - anoMilisegundos;
            Date data = new Date(milisegundosData);
            return DataUtil.toString(data);
        }

        @Override
        public int quantidadeSorteios() {
            return quantidadeMensal * 12;
        }
    };

    private static final long mesMilisegundos = 2_592_000_000L;
    private static final long trimestreMilisegundos = mesMilisegundos * 3;
    private static final long semestreMilisegundos = trimestreMilisegundos * 2;
    private static final long anoMilisegundos = semestreMilisegundos * 2;

    private static final int quantidadeMensal = 10;

    public abstract String dataString();
    public abstract int quantidadeSorteios();
}
