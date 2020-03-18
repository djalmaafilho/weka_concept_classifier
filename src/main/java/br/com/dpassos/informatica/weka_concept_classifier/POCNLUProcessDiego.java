package br.com.dpassos.informatica.weka_concept_classifier;

import java.io.File;
import java.io.FileWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import weka.core.Instance;
import weka.core.converters.CSVLoader;

public class POCNLUProcessDiego {

	public static void main(String[] args) throws Exception {
		System.out.println("INICIO");

		Map<String, Integer> newWords = new TreeMap<String, Integer>();

		Map<String, String[]> general = new TreeMap<String, String[]>();

		general.put("ACESSO",
				new String[] { " acess", " entrada", "entrar", "entrou", " entra ", "entrando", " saída", " porta ",
						"portal", "portaria", "portao", "gateway", " bloque", " chave", " fechadura", "abertura",
						"impedimento", " barrar", "barrado", "barrou", " muro ", " cofre ", "liberdade", "credencia", "adentrar", " emancipa " });

		general.put("AGRUPAMENTO",
				new String[] { "colecao", "conjunto", "grupo", "equipe", " unir ", "exercito", "contingente",
						"coletivo", "união ", "multirão", "colabora", " tropa ", " extrato", "extrati", "colegiado",
						"coleciona", "agrupa", "colecoes", "coleciona", "juntar" });

		general.put("AJUDA", new String[] { " auxíl", "ajuda", "socorr", "suporte", "help", "assiste", "assisti",
				"servir", "colabora" });

		general.put("AGUA_PROXIMIDADE", new String[] { "nascente", " foz ", "ribeir", "litorâ", "praia", "costa",
				"delta", "húmus", "oásis", "beira mar", "praie", "praia", " duna ", " dunas " });

		general.put("AQUATICO",
				new String[] { " rio ", " rios ", "riacho", " mar ", "oceano", " lago ", " lagoa ", " marinh",
						" maritm", "afluente", "efluente", "nascente", "córrego", "abissal", "barragem", " represa ",
						"irriga", "reservatorio", "vazao", "vazamen", "bacias", "hidrografi" });

		general.put("ANALISE_CLINICA",
				new String[] { "clinico", "exames", "seringa", "agulha", "prontuário", "material coletado",
						"diagnostico médico", "proagnostico", "laudo clinico", "relatorio médico", "posto de sau",
						"clinica ", "analise clinica", "soropositiv", "soronegativ" });

		general.put("ANIMAL",
				new String[] { "'", "animais", "bicho", "fera", "besta", "selvagem", "doméstic", "cativeiro",
						"jaula", " ave ", "pássaro", "peixe", "anfíbio", "réptil", " zoo", "orgânic", "elefante",
						"baleia", "rinoceronte", "cavalo", "girafa", "hipopótamo", "tubarão", "golfinho", "touro",
						"búfalo", "leão", "tigre", "vaca", " boi ", " gado ", "bufalo", "cobra ", " porco", "suino",
						"suina", "equino", "equina", "cetaceos", "cetacea" });

		general.put("ANTROPOLOGIA",
				new String[] { "hominideo", "antropo", "homo-sapiens", "anthropos", " human", "preconceito",
						"etnocentrismo", "sociologia", "psicologia", "linguist", "etnolog", "direitos humanos",
						"holocausto", "separatis", "xenofob", " ego" });
		
		general.put("ARQUITETURA",
				new String[] { "arquitet", " casa ", "predio", "edifício", "pirâmide", "zigurati", "palácio", "sobrado",
						"catedral", "monastério", "arranha céu", "castelo", "fortaleza", " forte de ", "templo", " cabana "
						," oca "});

		general.put("ARTE",
				new String[] { " arte ", "musica", "pintura", "poesia", " dança", " canto", "escultura", "teatro",
						"cinema", "escrita", "literatura", "espetáculo", "show", "artista", " ator ", " atriz ",
						" cantor ", " interpret", "expressao", "gravura" });
		

		general.put("BANCO_DADOS", new String[] {"SQL", "banco de dado", " DML ", "database", " ORM ", "objeto relacional", "DDL"
				, " procedure", " trigger" ," ETL", "SGBD", "modelo em rede", "modelo hierarquico", "modelo fisico", "modelo conceitual"
				, "ACID", "Atomicidade", " begin", "transaction", "Big data", "Business inteligence", " BI ", "(BI)"});

		
		general.put("BRINCAR",
				new String[] { " brinca", " sorri", " corre", " esconde", " suar ", " diversao", " divertir",
						" passa tempo", " jogos ", "partida", " game ", "gamefication", " jogo ", "humor", "recreio",
						"recrea" });

		general.put("BEBIDA", new String[] {"bebida", " suco", "vinho", "cerveja", "cachaça", "café", " chá ",
				"alcoólic", "refrigerante", "espumante", "champagne", "vinico" });

		general.put("BICICLETA", new String[] { "catraca", "corrente", "camara de ar", " celin ", " guidão ",
				"cabo de freio", " aro ", " pedal", "ciclis", "bicicl", "tricicl" });

		general.put("BIOLOGIA", new String[] { " bio", "fisiolog", " vivo ", " viva " });

		general.put("BOLSA_VALOR",
				new String[] { " acionista", "bolsa de valor", "majoritário", "compra de ações", "venda de açoes",
						"balanço empresarial", "balanco financeiro", " pregao ", "fundo imobiliario", "valor por cota",
						"potencial de retorno", "oferta publica", " IPO ", "mercado secundario", "valorizacao",
						"bovespa", "movimentacao", "corretora de valor", "balancete" });

		general.put("CABECA",
				new String[] { "maxilar", "boca", "dente", "denta", "lábio", "olho", "cabelo", "ouvido", "orelha",
						"nariz", "sobrancelha", " nuca ", "couro cabeludo", " testa ", " barba ", "bigode", "lingua",
						"faring", "laging", "otorrin", "derme ", " derma", "cilio", "sobrancelha", "palpebra", "narina",
						"labio", "cervical", "lombar", "costela", " pulso ", "arteria", " veia ", " veias ",
						"vaso capilar", "vasos capilares", "nasais", "nasal", "palat", " papila" });

		general.put("CALCADO",
				new String[] { "calçado", "sapato", "sandália", "chinel", "tênis", "tamanco", "mocacim", "alpercata" });
		
		general.put("CALCULO",
				new String[] { "derivada", "integral", "limite ", "linha curva", " área ", "equacao", "raíz quadrada",
						"potencia", "derivac", "logarit", "polinomi", "matriz", "determinante", " grau ", "régua",
						"infinito", "infines", " tende a" });

		general.put("CAMADAS_TERRA",
				new String[] { "núcleo", "mágma", "crostra", "atmosfer", "ionosfera", "mesosfera", "manto superior",
						"manto inferior", "litosfera", "rarefeito", "geleira", "circulo polar", "vulcao", "vulcanismo",
						"vulcoes", "erosao", "tempestade", "massas de ar", "previsao do tempo", "meteorolog",
						"correte de ar", "massa de ar", "correntes de ar", "nuvem", "nuvens", " maré", "superfície",
						"emergir", "imergir" });

		general.put("CANTO", new String[] { "lírico", "popular", "voz", "cantar", "cantor", "de canto ", "canto coral",
				"ópera", "tenor", "contralto", "soprano", "barítono" });

		general.put("CARRO",
				new String[] { "volante", "cambio", "caixa de marcha", "cinto de segurança", "odômetro", "combustível",
						"escapamento", "bateria", "embreagem", "capô", "porta mala", "pneu", "montadora", "veicular",
						"motorista", "ignicao", "banco do passageiro", "banco do motorista", "passageiro", });

		general.put("CASA",
				new String[] { "casa", " lar ", " morada", " morador", " morar", "residência",
						 "palafita", "abrigo", "habitat", " sala ", " quarto ", " cama ", " mesa ",
						" banheiro", " cadeira ", " garagem ", " cômodo ", " telhado ", " telha ", " porao ",
						"fogão", " toca ", " caperte ", " capacho ", " travesseiro ", " lencol "," guarda roupa ", " sotao ",
						" tapete ", "janela", " portao "});

		general.put("CELULA_ORGANICA",
				new String[] { "carioteca", "membrana", "cromossôm", "genét", " dna ", "enzima", " rna ", "proteica",
						"ribossomo", "lisossomo", " golgi", " celula", "carionte", " cariot", "citoplasma", "filamento",
						"orgânela", "hemac", "hemato", "fagocito", "endocito", "membrana plasmática",
						"sintese proteica", " plasto", "endocitose", "fagocitose", "pinocitose" });

		general.put("CIDADANIA",
				new String[] { "direitos", "dever", "cidadania", "civismo", "individuo", "cidadao", "obrigacao",
						"nacional", "sufragio universal", "maioridade", "servico militar", "alistamento",
						"elegibilidade", "naturalidade", "identidade", "fidalgo", });

		general.put("CLASSE_SOCIAL", new String[] { "classe social", "rico", "pobre", "periferia", "narrativa",
				"ideologia", "rei ", "escravo", "pirâmide" });

		general.put("CLASSE_ANIMAL", new String[] { "vertebrado", "invertebrado", "carnivo", "herbívoto", "mamífero",
				"reino", " filo ", "classe", "órdem", "família", "gênero", "espécie", "simbio" });

		general.put("CLIMA", new String[] { "árido", "tropic", "equatori", "glacial", "temperad", "desert",
				"mediterrâneo", " polar ", "sertão" });

		general.put("CIDADE", new String[] { " cidade ", "capitais", "mobilidade", "urban", "lopole", "pólis",
				"periferia", "centro", "esgoto", " coleta", "parque", "cotidian", "metropol" });

		general.put("COMBUSTIVEL", new String[] { "oxido", "eletrolise", "comburente", "combust", "renovavel", " gas",
				"oleo", "carvao", "propano", "metano", "butano", "meteno", "alcool", "diesel" });

		general.put("COMIDA",
				new String[] { "legume", "verdura", "fruta", "ortaliça", "massa", "carne", "vegetariano", "receita",
						"culinária", "nutriente", "gordura", "lipid", "leite", "café", " pão", " bolo", "pastel",
						"esfirra", "geleia", "manteiga", "queij", "nutri" });

		general.put("COMIDA_REGIONAL",
				new String[] { " doce ", "queijo", "comida regional", "tapioca", "cuscuz", "prato", "culinária local",
						"culinária regional", "feij", "arroz", "milho", "mandioca", "pizza", "bolo de ", "prato de " });

		general.put("COMERCIO",
				new String[] { "compra", "venda", " troca", "mercado", "produto", "preço", " loja", "comércio",
						"consumo ", "escambo", "market", "cliente", "fornecedor", "vendedor", "consumidor", "avalia" });

		general.put("COMPARACAO",
				new String[] { "diferente", "comprido", " largo", "estreito", " fino ", " alto", " medio ", "robusto",
						"diverge", "converge", "a montante", "a jusante", " igual ", " parece ", "parecid", " lembra ",
						"homogen", "heteroge", "hibrido", "isometr", "semelhan", "proporcao", " fracao ", "similar",
						"fraciona", "magnitu", "alcance", "amplitude" });

		general.put("COMPETICAO", new String[] { "ganhar", " perder", " vitori", " derrot", " empate", " empata",
				" comemora", " supera", " alcanca", "competir", "competicao", "competidor", "licitacao", "competit" });

		general.put("COMPORTAMENTO",
				new String[] { "comportamento", "atitude", "impulso", " motivo ", "motivacao", "atitude ", "zelo",
						"despres", "cuidar", "cuidado", "esqueci", "esquecer", "positividade", "coerencia",
						"personalidade", "solidari" });

		general.put("COMPUTADOR",
				new String[] { "computador", "mouse", "teclado", "monitor", "screen", " disk ", " tela ", "periférico",
						"notebook", "personal computer", "main frame", "nuvem", "hard disk", "fonte de alimentação",
						"computac" });
		general.put("CONCLUSAO", new String[] { " feito ", " conclui", " acaba", "termina", " final", " complet",
				"100%", "totalidade", " entregue", " aceito", " interrompi", " faltan", " vencido", " praso" });

		general.put("CONTRADICAO", new String[] { " mas ", "porém", "contudo", "todavia", "no entanto", "entretanto",
				"contrariamento", "em desacordo", "oposicao", "opositiv" });

		general.put("CONFIRMACAO", new String[] { " Sim", " certo", " afirma", " positiv", " negativ", " é ", "ambi",
				"confirm", "prova" });

		general.put("CONTINENTES",
				new String[] { "africa", "america", "ásia", "europa", "oceania", "artico", "antarti" });

		general.put("CONSTRUIR",
				new String[] { " sintese", " sintéti", " produzir ", " constru", " fazer ", " realiza", " faz ",
						" refaz", "etapa", "constitui ", "constituido", "constituir", "laboratóri", "desenvolv",
						"confecc" });

		general.put("COR", new String[] { "branc", "azul", "pret", "cinz", "verde", "amarel", "lilas", "roxo", "laranj",
				"púrpura", "violet", "vermelh", "marron", "béje", "negro", "pigment", "color" });

		general.put("CORPO",
				new String[] { "cabeça", "tronco", "membro", "braço ", "perna", " mão ", "pescoço", " pé ", " corpo ",
						"abdome", "barriga", "costas", " rins ", "renal", "exofago", "estomago", "coracao", "pulmao",
						"cardio", "cerebro", "cerebral", "pulmonar", "pulmoes", "timpano", "figado", "bexiga",
						"esqueleto", "granio", "intestin", "pancrea", "colna cervical", "espinha dorsal", "cerebelo",
						"antebraco", "umbigo", "umbilical", "placenta", "aparelho digestivo", "aparelho reprodutor",
						"axonio", "neuronio", "corporal", "corporeo", " corpo ", "cortex", "podes ", "podo ", " cranio " });

		general.put("CORPOS_CELESTES",
				new String[] { "estrela", "planeta", "asteroide", "lua ", "constela", "galáxia",
						"estelar", "massivo", "telescópio", "astrono", "órbita", "gravidade", "gravit", "eclipse",
						" cometa ", " marte ", " planeta terra " , " sol ", " buraco negro ", " supernova "});

		general.put("CRUSTACEO", new String[] { "crustace", "carapaca", "invertebrado", "placton", "bentonic",
				"branchiura", "cirripedia", "artropode", "rimipedia", "cuticula", "parafiletico", "monofiletico", " poliqueta " });

		general.put("CULTURA", new String[] { "laser", "folclo", "religi", "comida", "costum", "regional", "popular",
				" cultura ", "cultural", "culturais", "tradic", "carnaval", "reveillon", "cantiga" });

		general.put("DESEJO", new String[] { "querer", "vontade", "desej", "pretenc", "objetiv", " alvo ", "quer ",
				" meta ", "esperado", "exito" });

		general.put("DESIGNE_SYSTEM", new String[] { "token", " tag ", " css ", "design", "cores", "estilo", " cor " });

		general.put("DIMENSAO", new String[] { "dimensão", "altura", "largura", "espess", "profund", "peso",
				"comprimento", "tamanho", " medidas", "metria", "metric" });

		general.put("DINOSSAURO", new String[] { "dinossauro", "ssauro", "jurássico", "ssaurídeo", " dinossauria " });

		general.put("DIFICULDADE", new String[] { " duro ", " dificil", " dificul", " complica", "desgastante",
				" facil", " simpl", "trabalhos", " esforç", "hercule", "possível" });

		general.put("DINHEIRO", new String[] { "grana", "dinheiro", "remunera", "pagar", "dívida", "débito", "compra",
				"moeda", "$", " ouro ", " prata ", " cobre ", " bronze ", "monetari", "monetiz", "financ" });

		general.put("DOCUMENTOS_PESSOAIS",
				new String[] { "carteira de trabalho", " CPF ", "carteira de identidade", "passaporte", " rg ",
						"carteira nacional de habilitação", " cnh ", "carteira de estudando", "carteira de habilitacao",
						"registro de nascimento", "reconhecimento de firma", "copia autentiticad", " CNH ", " CIC ",
						" CGC ", "Certidao de negativa", "certidao de nasccimento", "comprovante de alistamento",
						" CAM " });

		general.put("DOCUMENTAR",
				new String[] { "documento", "achado", "registro", "escrito", "fóssil", "arqueo", "arcai", "gravar",
						"gravação", "antig", "arquitetoni", "escavacao", " paleoz", "paleobot", "paleoeco", "museu",
						"grafia", "grafar", "registrar", "registrado" });

		general.put("DOENCA", new String[] { "enfermidade", "vírus", "célula", "infecç", "acompanhamento", "doente",
				"patologia", "remédio", "droga", " dor ", "hospital", "internar", " leito", "cirúrgia", "tratamento",
				"doença", "clínic", "bactér", "fungo", "fungi", "parasit", "patogên", "mutila", "ameba", "paramerci",
				"terapia", "terapico", "terapeuta", "coccus ", "aids", "cancer", "tuberculo", "gripe", "resfrriado",
				"dor de cabeca", "feber", "vomito", "nausea", "falta de hapetite", "indisposic", "cancasso", "fraquesa",
				"cefaleia", "contagi", "hipertensao", "candida", "diabet", "diarrei", "dosagem", "conjuntiv", "micose", "necrose",
				"hpv", "hiv"});

		general.put("DOENCA_TERAPIA", new String[] { "insulina", "antidepressiv", "antitermico", "antibiotico",
				"corticoide", "analgesico", "quimio", "radioterapi", "anti" });

		general.put("DROGA",
				new String[] { "droga", "alucin", "maconha", "crack", "cocaina", "tráfico", "trafica", "psicoativ",
						"opiáceos", "substancia", "tabaco", "nicotina", " opio ", "papoula", "cannabis",
						"principio ativo", "sintetica", "SVS/MS 344/98", "depressivo", "depressor", "estimula",
						"dependente quimico", "dependencia quimica" });

		general.put("ECONOMIA",
				new String[] { "economia", "gasto", "economic", " PIB ", " bancos ", "superavit", "defict",
						"balança comercial", "taxa de juro", "banco central", "inflacao", "superavit primario",
						"imposto", "aliquota" });
		
		
		general.put("EDIFICACAO",
				new String[] { " piso ", "assoalho", " teto ", "elevador", "parede", "tijolo", "laje", "treliça",
						" viga ", " pilar ", "heliponto", "mesanino", "escadaria", "corta fogo",
						 "fachada", "luminária", "pedreiro", " reboco ", "azulejo", " ceramic", " ceramist",
						"tomada eletrica", "camara" });

		general.put("EMPRESA", new String[] { "sócio", " dono ", "fundador", "capital", "lucro", "prejuízo", "balanço",
				"corporacao", "corporati", "fluxo de caixa", "hierarqui", "companhia " });

		general.put("EMPREGO",
				new String[] { "emprego", "trabalhador", "funcionári", "jornada", "labuta", "trabalho formal",
						"trabalho informal", "patrao", "empregado", "trabalhis", "bater ponto", "trabalhado autonomo",
						"pessoa juridica", "CLT", "profissional", "capacitacao profissional", "contrato de trabalhao",
						"ocupacional", "demissao", "demiti", "emprego", "carreira", "profissao", "admissao",
						"carteira assinada", "ferias", "decimo terceiro", "salario minimo", "previdencia soacial",
						"aposenta", "tempo de serviço", "prestacao de serviço", "FGTS" });

		general.put("ENERGIA",
				new String[] { "energia cinetica", "energia potencial", "elastica", "gravitacional", "nergia termica",
						"energia quimica", "energia eólica", "energia nuclear", "fusão", "fissão", " gerador " });

		general.put("ENSINO",
				new String[] { "ler", "escrever", "aprender", "professor", "escola", "faculdade", "universidade",
						"aluno", "aula", "docente", "dicente", "pedago", "cogni", "profiss", "formacao", "diploma",
						"certificado", "mentor", "leciona", "licenciatura", "cátedra", "didatic", "celegio",
						"alfabetiza", "academi" });

		general.put("ELETRICIDADE",
				new String[] { "enérgia", "corrente", "elétric", " volt ", " ampere ", " choque ", "bobina", " imã ",
						" indutor ", "indução", " capacit", " resist", "circuito", "magnét", " condut", "sensor",
						"corrente contínua", "gauss" });

		general.put("ELETRICIDADE_ENGENHARIA",
				new String[] { "transformador", "campo elétrico", "campo magnético", "tensão eletrétric", "voltagem",
						"amperagem", "load baster", "subestação", " TC", " TP", "de corrente", "de potencia", "dieletr",
						"inversor", "fásico", "fasor", "indução", "indutor", "isola", "de manobra", "fase",
						"corrente contínua", "abertura de chave", "fechamento de chave", "talabarte", "para-raio",
						"aterramento", "medidor de energia", "eletrotecnic", "eletric", "tarifa", "cabeamento", " fio ",
						" fiacao ", " fios ", "isolante", "disjuntor", "fusível" });

		general.put("ELETRONICA",
				new String[] { "semicondutor", "eletronic", "componente", "diodo", "trasistor", "chip",
						"circuito integrado", "processador", "placa ", "display", "piezo", "cristal", "fásico",
						" diodo ", "tiristor", "digital" });

		general.put("EQUIPAMENTO_SOM", new String[] { "microfone", "pedestal", "placa de som", "audio", "captador",
				"equaliza", "fone de ouvido", "auto-falante", "auto falante", " sonda ", " sonar ", " eco" });

		general.put("ERAS_GEOLOGICAS",
				new String[] { "arqueozoi", "proterozoi", "paleozoi", "mezozoi", "cenozoi", "pangea",
						"formacao dos continentes", "era do gelo", "geologica", "cretáceo", "neolit", "paleolit",
						"mesozoic", "era glacial" });

		general.put("ERGONOMIA",
				new String[] { "ler", "lesão", "esforço", "repetitivo", "postura", "ergonômi", "fisioter" });

		general.put("ESCRITOS", new String[] { "livro", "artigo", "revista", "jornal", "resenha", "folheto" });
		
		general.put("ESPORTE_RADICAL", new String[] { " salto"," Saltar", " cair", " queda", "pular", "jogar-se",
				"impacto", "aceleracao", "tunel de vento", "oculos de protecao", "grade altura", "instrutor de salto",
				"asa delta", "body jump","trilha ", "acampa", "escotismo", "escoteiro",
				"canoagem", "surf", "triatle", "bibicross", "Skate", "skatis", "Rafting", "voo livre", "Rapel", "paraquedis",
				"Mountain bike", "Arborismo", "parkour"});
		
		general.put("ESTACOES_ANO", new String[] { "primavera", "verão", "outono", "inverno" });

		general.put("ESTADOS_BRAIL",
				new String[] { "rio grande do sul", "santa caratina", "parana", "sao paulo", "rio de janeiro",
						"minas gerais", "curitiba", "bahia", "alagoas", "pernambuco", "paraiba", "sergipe",
						"pernambuco", " ceara", "rio grande do norte", "tocantins", "mato grosso", "brasilia",
						"maranhao", "amapa", "roraima", "amazonas", "acre", "goias" });

		general.put("ESTATISTICA",
				new String[] { "estatística", "gráfico", "descritiva", "dados", "consolida", "inferência", "demografi",
						"modelo", "critério", "amostra", "desvio padrao", " media", " mediana ", "erro ",
						"intervalo de confianca", "dispersao", "normalizacao", "probabili", "estimar", "estimativa",
						"modelar", "prever", "previsao", "sumarizacao", "curva normal", " PCA ", "Bayes", " vies ",
						" envies", "estratato", "estratifica" });

		general.put("ESCOLA",
				new String[] { "escola", "sala de aula", "aula", "pátio", "merenda", "recreio", "intervalo", "diretor",
						"coordenador", "pedagog", "secretaria", "currículo", "matérias", "disciplina", "assunto",
						"formação", "notas", "quadro negro", "lousa", "cadeira escolar" });
		general.put("ESPORTE",
				new String[] { "exercício", "fisico", "atleta", "esporte", "jogo", "partida", "competição",});

		general.put("ENGENHARIA_PRODUCAO",
				new String[] { "acessoria do produto", "acesso dedicado", "acidente de consumo", "corretiv", "preventi",
						"manuten", "acondicionamento", "controle de estoque", "custo efetivo", "logistic",
						"segurança do traba", "cesmit", " EPI ", "engenharia de produção", "cadeia produt",
						"planta da fábrica", "linha de pro", "curva de banheira", " gestão ", " armaze" });

		general.put("EXPLICACAO", new String[] { " pois ", "porque", "concluo", "significa", " logo ", "assim",
				"dito isto", "então", "dessa forma", "por isso", "podemos perceber que", " então ", "fica claro" });

		general.put("EXCLUSAO", new String[] { "remove", "deleta", "jogar fora", "exclui", "restringe", "anula",
				"remocao", "restri"});

		general.put("FAMILIA",
				new String[] { "casal", "casamento", "familia", "matrimônio", "separacao", "pensão", "filhos",
						"divórcio", "filho", "filha", "pai", "mãe", " neto ", " neta ", " avo ", " avós ",
						"descendente", "descendencia", "linhagem", "herdeiro" });

		general.put("FENOMENO_NATUREZA", new String[] { "chuva", "chuvo", "chuvi", "chove", "raio", "vento", "clima",
				"meteorolog", "desastre", "cataclisma", "tsunami", "tremor de terra", "cismico", "terremoto" });

		general.put("FESTA",
				new String[] { "festa", "orquestra", "dança", "balada", "fanfarra", "trio elétrico", "carro de som",
						"palco", "radiola", " DJ'", "mesa de som", " MC'", " bolo ", "comemora", "aniversário",
						"parabéns", "confrater" });

		general.put("FISICA", new String[] { "dimenssão fisica", " astronom", "espaço tempo", "estado da matéria",
				" luz ", "ótica", "óptic", " termico", "termodina", "mecânic", " potencia ", " solidos ", " gasoso",
				"forca de atrito", " inercia", "quantidade de movimento", "Celsius", "Fahrenheit", "Kelvin", "(SI)",
				"sistema internacional de unidades", "termométrica", "termodinamica", "de Condução", "por Condução",
				"Convecção", "Irradiação", "Calor sensível", "Calor latente", "dilatação", "estado de agregação",
				"estado físico", "coeficiente de dilatação", "Dilatação linear", "Dilatação superficial",
				"Dilatação volumétrica", "entropia", "Calor específico", " calor", " frio", "fogo", " plasma " });

		general.put("FISICA_FORCA",
				new String[] { "força eletrica", "forca aplicada", "forca sobre", "forca exercida", " aceleracao ",
						"energia potencial", "energia cinetica", "mecânic", " potencia ", "movimento retilineo",
						"movimento uniforme", "velocidade media", "velocidade instantanea", " trajetoria", "newton",
						"fisica classica", "alavanca", " roldana", "algebra fasorial", "coeficiente de atrito",
						"forca de atrito", " inercia", "quantidade de movimento" });

		general.put("FISICA_ONDA",
				new String[] { "física", "frequencia de onda", " ondas ", " ondulató", "cordas", "pendulo" });

		general.put("FISICA_LIQUIDO",
				new String[] { "volume deslocado", "volumetri", "dilatacao", "dilatado", "coeficiente de dilatacao",
						"empuxo", "ebulicao", "condensacao", "calefacao", "evapora", " vapor", "solidifica", " fusao ",
						" derret", "sublima", "liquefa" });

		general.put("FISICA_MODERNA",
				new String[] { "quantic", "teoria da relatividade", "espaco X tempo", "fotoeletrico", "corpo negro",
						"plank", "eistein", "espectro", "foton", "entrelacamento", "emc2", "velocidade da luz",
						"ano luz", "fisica moderna", " partícula ", "particulas" });

		general.put("FORCAS_ARMADAS",
				new String[] { "bombeiro", "exército", "marinha", "aeronautica", "polícia militar", "polícia civil",
						"soldado", "recruta", "coronel", "capitão", "comandante", "almirante", "major", "delegado", });

		general.put("FORMAS",
				new String[] { " linha ", " círcul", " triângular", " quadrado", "pentagono", "hexagono", " cubo ",
						" cilindr", " esferico", "conico", " toróide", "agono", "angul", " aresta", "edro ", "trapesio",
						"paralelogramo", "paralelepidepdo", "losango", "bola", "globo", "globular", "cubico", "cubica",
						"lineo", "linea", "superfici" });

		general.put("GENETICA",
				new String[] { "genétic", "gameta", " mutacao", "seleção natural", " evolut", "molecula",
						" epidemiologia", "taxonomi", "eugenia", "ribonucle", "codigo genetico", " gene", " timina",
						" guanina", " citosina", "uracila", " amina" });

		general.put("GENITALIA_E_EROGENAS",
				new String[] { "pênis", "vagina", "ânus", "púbis", "glúteo", " anal ", " útero ", "testículo",
						" pubian", "genita", "vulva", "petasma", " telico ", " eroti", "mamilo", "ovário",
						"grandes labios", "pequenos labios", "seios", "clitoris", " glande ", "prepucio",
						"zona carnosa", "erecao", "lubrificacao", "orgasmo", "erogen", "fluido vaginal", " semen ",
						"seminal", "pubian", "prostat", "pelvi", "trompas de falopio", "nadegas", "monte de venus",
						"uretra", "perianal" });

		general.put("GESTAO",
				new String[] { "organizacoes", " gestão", "gerir", "gestor", " gerencia", " administrar",
						"processo decis", "demanda", "gerente", "coordena", "chefe", "chefia", "lider", "coach",
						"recursos humanos", " RH ", "colaborador", "funcionario" });

		general.put("GEOMETRIA",
				new String[] { "pitagor", "distancia euclidi", "o ponto", "determinado ponto", " reta ", " plano carte",
						"dimensiona", "a dimensao", " quadrado", " retangu", " triang", " cartesi", " diágon", " raio ",
						" circul", " esfer", " tangen", " perpendicular", "posicao relativa", "comprimento", "area",
						"volume", "geometri", "calculo integral", "axioma", "linha curva", "linha reta",
						"segmento de reta", "paralelismo", " angulo", " angular", " vetor", " escalar", "central",
						"concentri" });

		general.put("HIGIENE", new String[] { "banho", "escova", "pentea", "lavar", " aceio", " higia", " higien",
				"limpesa", "limpar", "sabão", "sabonete", "limpo", "lavagem", "enxagu", "detergente", "limpador" });

		general.put("HISTORIA", new String[] { " data ", "acontecimento", " evento ", " fato ", "passado", " momento ",
				"civiliza", "historiador", "povos", "histori", "colonial", "coloniza" });

		general.put("HORA", new String[] { "minuto", "segundo", "hora", "milisegundos" });

		general.put("IGREJA",
				new String[] { " padre", "pastor", "sacerdote", "igreja", "diacono", "presbitero", " papa ", "monje",
						" crente", "evangelho", " clero ", "inquisicao", "crucificacao", "cruzadas",
						"companhia de jesus", "jesuita", "jesus", "catequiza", "comunhao", "batismo", "baptismo",
						" batiza", " culto ", " adora", " biblia", "alcorao", " torá ", "congregac", " seita",
						"doutrina", "dizimo", "sacrificio", "puntific", "novo testamento", "antigo testamento",
						"religiosi", "membro da igreja", " cruz" });

		general.put("INDUSTRIA",
				new String[] { "fábrica", "insumo", "fornecedor", "produção", "manufatura", "industria",
						"setor primário", "bens de consumo", "materia-prima", "bens duráveis", "bens semi-duráveis",
						"bens não-duráveis", "taylor", "ford", "toyot" });

		general.put("INFORMATICA", new String[] { "designe", "algorítmo", "lógica", "computação", "informatica",
				"aplicativo", "sistema computa", "processamento" });

		general.put("INFORMATICA_SUPORTE", new String[] { "wireless", "roteador", " LAN ", " WAN ", "hardware",
				"periférico", "internet", "computador", "celular", "drive", "blowfish", "online", "web" });

		general.put("INTELIGENCIA_ARTIFICIAL",
				new String[] { "rede neural", "deep learning", "aprendizado de máquina", "big data", "data mining",
						"analytics", "machine learning", "redes neurais", "multicamada", "aprendizado profundo",
						"classificação", "predicao", "clusterizacao", "supervisionado", "por reforco", "reinforcement",
						"classificador", " label ", "markov", "confusion matrix", "matriz de confusao", "acurácia",
						"recall", "agente inteligente", "multiagente", "agentes inteligentes" });

		general.put("INSTRUMENTO_MEDICAO", new String[] { "calibra", "exatidao", "precisao", "resolucao",
				"equipamento padrao", "erro de paralaxe", "medidor digital", "medidor eletromecanico", "analogico" });

		general.put("INSTRUMENTO_MUSCIAL", new String[] { "corda", "sopro", "percurssão", "melodia", "armonia", " kar ",
				" midi ", "acústico", " afina", " tonal", " ton ", "timbre", "luthier", "instrumento music" });

		general.put("INSETO", new String[] { "abelha", "formiga", "borboleta", "inseto", "mosca", "mosquito",
				"pernilongo", "inseticida", " escorpi", "aracn", "besouro" });

		general.put("IDIOMA",
				new String[] { "dialet", "lingua", "tradução", "intérprete", "inglés", "português", "francês", "falar",
						"idioma", "intercambio", "dicionário", "gramática", "sintáxe", "semântica", "palavra", "fonema",
						"vernácul", "estrangeiris", "sufix", "prefix" });

		general.put("JORNALISMO",
				new String[] { "reporter", "reportagem", "noticia", "divulgac", "matéria jorn", "comentarista",
						"jornal", " news ", " cronica ", "resenha", "redator", "cronista", "imprensa",
						"comunicacao de massa", "fonte de informação", " leitor ", "credibilidade" });

		general.put("LEIS",
				new String[] { "legisla", "forum", "justiça", "crime", "juiz", "polícia", "processo", "fiscal", "fisco",
						"constituição", "constitucio", "tribut", "petição", "advoga", "forense", " OAB ", "jurispruden",
						"comarca", "acusacao", " reu ", " dever ", " deveres ", " direito ", " direitos ", "advoca",
						" promotor", "processo penal", "processo civil", " ilegal ", " legal ", "burocra", "juricia",
						"suprema corte", "corte marcial", " lei ", "juridic", "leis" });

		general.put("LEITURA", new String[] { "texto", " textua", "poesia", "poema", "poeta", "poetiza", "escritor",
				"obra", "literário", "literatura" });
		general.put("LIGACAO_QUIMICA",
				new String[] { "tabela periódica", "ligação metalica", " iônic", " octeto", " atomic",
						"elemento químic", "valencia ", "gás nobre", "ligacoes quimicas", " metal", " ametal",
						" cation", " anion", " catodo", " anodo" });
		
		general.put("LINGUAGEM_PROGRAMACAO", new String[] {"linguagem de ",  "compila",  "código de maquina", "binário", "algoritmo",
				"lógica", "codigo binario", "codigo compilado", "escrita de codigo", "codificacao", " UML ", "JSON", "XML", "html", 
				" SOA ", " GIT ", "versionamento", " Agile ", "WaterFall", "application", " TDD ", " BDD ", "teste unitario", 
				"unit test", "cluster", "normaliza", "teste integra", "e2e", "multiplataforma", "crossplataforma", 
				"linguagem funcional", "orientacao a aspecto", "procedural", "REST ", "W3C",
				 " boolean", " boleano ", "framework", " lib ", " java",  " API "});

		general.put("LIQUIDO",
				new String[] { "aqua", "água", "aquos", "húmid", "soluve", "solven", "solver ", "dilui", " gota ",
						"gotícula", " chuva ", " dissolv", "enchente", "alagamento", "algar", "alagado", " aquifer",
						"lencou freatico", "fonte termal", "cacimba", "poço artesiano", "absolv", "absorv", "hidrat",
						"desidrat" });

		general.put("LITERATUA",
				new String[] { "renascimento", "renascen", "barroc", "romanti", "modernis", "impression", "abstrat",
						"realis", "naturalis", "ufanis", "trovador", "quinhetis", "seissentis", "moderno" });
		general.put("LOCAL",
				new String[] { "condomínio", " rua ", "bairro", " cidade ", "município", "distrito", " ilha ",
						" estado ", "país", " região ", "continente", "continental", " local ", "municipa", "estadua",
						" federa", "aldeia", "arquipelago", "povoado", "interior", "urbano",
						" lugar ", " espaco ", " zona "});

		general.put("LUTA", new String[] { "esforço", " luta", "guerra", "conflito", "arma ", " tiro ", "bomba",
				"confronto", "enfrenta", " baixa", " comando" });

		general.put("MACRO_ATIVIDADES_ECONOMICAS",
				new String[] { "insdustria", "agricultura", "pecuária", "primário", "secundário", "terceário",
						"extrativismo", "pesca", "psicultura", "cultivo", "icultura", "criação", "sazonal" });
		general.put("MAQUINA", new String[] { "máquina", "auto", "carro", "robô", "navio", "motor", "dispositivo",
				"ferramenta", "sistema", "equipamento " });
		general.put("MATEMATICA",
				new String[] { "equação", "fórmula", "enunciado", "multiplicação", "adicação", "subtracação", "divisão",
						"geometria", "calcul", "algebr", "axioma", "deducao", "deduz", "euclid", "numero", "kepler",
						"fracoes", "matematic" });
		general.put("MATERIAL_ESCOLAR", new String[] { "caneta", "lápis", "caderno", "borracha", "livro", "mochila",
				" farda ", "regua", "apontador", "mochila", "papel", " cola ", " tesoura ", " estojo " 
				, " corretivo ", " lapis de cor ", " esquadro ", " transferidor ", " compasso "});

		general.put("MATERIAIS", new String[] { "ferro", " aço ", "bronze", " cobre ", "chumbo", "níquel", "matériais",
				"óleo", "couro", "plastico", "resina", "alumin", "miner", "polimeto", "polipropileno", "mercurio" });

		general.put("MATRIZ_ENERGETICA", new String[] { "matriz energética", "cota de carbono", "hidro elétrica",
				"termelétrica", "térmic", "fonte energética", "solar", "eólic", "biomassa" });
		general.put("MEIO_AMBIENTE", new String[] { "natur", "meio ambiente", "habitát", "ecossistema", " ecolog",
				"ambienta", " bioma ", "golfo", "fauna", " flora" });

		general.put("MEMBROS",
				new String[] { "bícep", "tricep", "panturrilha", "joelho", "antebraço", "perna", "cintura", "braço",
						"cotovel", "calcanh", "tíbia", "tornozel", " dedo ", " dedos ", " unha", "falange", "femur",
						"tibia", "cotovelo", "membro superior", "membro inferior", "membros superiores", "membros inferiores",
						" superior", " inferior"});

		general.put("MOLUSCO", new String[] { "molusco", "ostra", "caramujo", "polvo", " lula", "ventosa", "manto",
				"caracol", "caracois", "gastropode", "cefalopode", "radula", "bivalve", "escafopode", "malacologia", " mollusca " });

		general.put("MOVIMENTO", new String[] { " corre", " anda", " parar", "veloz", " lent", "rápid", "viagem",
				"viaj", "acelera", "continu", "nadar", "voar", "vôo", "desloca", "movel ", " mover ", " mexe" });
		general.put("MIDIAS",
				new String[] { "tv ", "televis", "rádio", "blog", "rede socia", "vlog", "podcast", "jornal", "vídeo" });
		general.put("MUDANCA", new String[] { " difer", " alter", " perturb", " muda ", " mudan", " diverssif",
				" transforma", "metamorf", "muta", "diversa ", "diversas" });

		general.put("MUSICA",
				new String[] { "ritm", "som", "sonor", "canto", "canta", "percuss", "instrument", "música", "partitura",
						"tablatura", " acorde", "nota musical", "compasso", "sinfoni", "harmoni", "melodi", "rap", "rock" });
		general.put("NECESSIDADE_PRIMARIA",
				new String[] { "respira", " aliment", " água ", " sede ", " come ", " bebe " });
		general.put("NECESSIDADE_SECUNDARIA", new String[] { " casa", " morad", "calçado", "vestuário", " abrigo" });

		general.put("NIVEL_ENSINO", new String[] { "ensino básico", "ensino fundamental", "ensino médio", "universi",
				" mestr", " graduac", "especializa", "doutor" });

		general.put("NUMERO", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"});

		general.put("OCEANO", new String[] { "atlântico", "indico", "pacífico", "artico", "glacial" });
		
		general.put("ONDULATORIA", new String[] { "propagar", "propagacao", "propagado", "eletromagne", "dimensional",
				" vibra", "longitu", "transvers", " corda", " vácuo", "comprimento de onda" });

		general.put("ORGANIZAR",
				new String[] { "processo", "organizar", "arruma", "metodologi", "organizado", "correta", "corretud",
						"retifica", "ajust", "corrig", " fila ", "enfileira", "agenda", "planeja", "BPMS", " BPM ",
						"gerente", "coordena", "administra", "relatorio" });

		general.put("ORIENTACAO_OBJETOS",
				new String[] { " POO ", "orientacao a objeto", "tipo de variável", "polimorfismo", "encapsulamento",
						" atributo", "instancia", " estatico", "método acessor", " geter", " seter", "interface",
						"superclasse", "subclasse" ,"classe pai", "classe filha", "heranca "});

		general.put("ORIGEM", new String[] { "copia", "clone", "clona", "gemeo", "xerox", "origina", "origem" });

		general.put("OTICA",
				new String[] { " lente ", "prisma", "caleidoscopio", "difracao", "refracao", " refrat", "convex",
						"concav", "espelho", " difuso", " difusa", "refrigente", "reflex", "opaco", "opaci", "optic",
						"otic", "reflet", "transparen", "transluci" });

		general.put("PAIS", new String[] { "China", "Índia", "Estados Unidos", "Indonésia", "Brasil", "Paquistão",
				"Nigéria", "Bangladexe", "Rússia", "México", "Japão", "Etiópia", "Filipinas", "Egito", "Vietname",
				"Congo", "Alemanha", "Irão", "Turquia", "Tailândia", "Unido", "França", "Itália", "Tanzânia", "Mianmar",
				"Quénia", "Colômbia", "Espanha", "Argentina", "Uganda", "Ucrânia", "Argélia", "Sudão", "Iraque",
				"Polónia", "Canadá", "Afeganistão", "Marrocos", "Saudita", "Peru", "Venezuela", "Usbequistão",
				"Malásia", "Angola", "Moçambique", "Nepal", " Gana ", "Iémen", "Madagáscar", "Marfim", "Austrália",
				"Camarões", "Taiuã", "Níger", "Lanca", " Faso", "Roménia", "Maláui", "Mali", "Cazaquistão", "Síria",
				"Chile", "Zâmbia", "Guatemala", "Baixos", "Zimbábue", "Equador", "Senegal", "Camboja", "Chade",
				"Somália", "Guiné", "Sul", "Ruanda", "Tunísia", "Bélgica", "Cuba", "Benim", "Burúndi", "Bolívia",
				"Grécia", "Haiti", "Dominicana", " Checa", "Portugal", "Suécia", "Azerbaijão", "Jordânia", "Hungria",
				"Unidos", "Bielorrússia", "Honduras", "Tajiquistão", "Sérvia", "Áustria", "Suíça", "Israel", "Guiné",
				" Togo", " Leoa", "Bulgária", "Laus", "Paraguai", "Líbia", "Salvador", "Nicarágua", "Quirguistão",
				"Líbano", "Turcomenistão", "Singapura", "Dinamarca", "Finlândia", "Eslováquia", "Brazzaville",
				"Noruega", "Eritreia", "Palestina", "Rica", "Libéria", "Ásia", "Irlanda", "Zelândia", "Africana",
				"Mauritânia", "Cuaite", "Croácia", "Panamá", "Moldávia", "Geórgia", "Herzegovina", "Uruguai",
				"Mongólia", "Albânia", "Arménia", "Jamaica", "Lituânia", "Catar", "Namíbia", "Botsuana", "Lesoto",
				"Gâmbia", "Macedónia", "Eslovénia", "Gabão", "Letónia", "Bissau", "Cosovo", "Barém", "Suazilândia",
				"Tobago", "Equatorial", "Estónia", "Maurícia", "Chipre", "Jibuti", "Fiji", "Comores", "Butão", "Guiana",
				"Montenegro", "Salomão", "Luxemburgo", "Suriname", "Verde", "Micronésia", "Maldivas", "Brunei", "Malta",
				"Bahamas", "Belize", "Islândia", "Barbados", "Vanuatu", "Príncipe", "Lúcia", "Quiribáti", "Granadinas",
				"Tonga", "Granada", "Barbuda", "Seicheles", "Andorra", "Dominica", "Neves", "Samoa", "Marechal",
				"Mónaco", "Listenstaine", "Marinho", "Palau", "Nauru", "Tuvalu", "Vaticano", " ONU ", " OTAN ", " OEA ",
				"Uniao Europeia", "Nações unidas", "diplomacia", "embaixad" });

		general.put("PEIXE", new String[] { "peixe", "aquário", "barbatana", "guelra", "branqui", "nadadeira", "escama",
				" pesca", "aquacultura", "tubarao", "ictiologia", "pesqueir", "psicultura", "viveiro", });

		general.put("PERIGO", new String[] { "risco", "arriscado", "fugir", " medo ", "predador", "caça",
				"defesa", "escapar", "ameaca", "autopreservao", "legitima defesa", "defender",
				" fuga ", " perda", " dano ", "danifi", "ferir", "ferid", "ferrimento", "feroz", "indoma",
				"mortal", "critico", "criticidade", "inflamavel", "danger", "explosivo", "periculo",
				"arisca", "imprevisi", "afasta", "distanciamento", "perigos", "fatal"});

		general.put("PERGUNTA",
				new String[] { "pergunta", " entrevista", " pesquisa", " consulta ", " pesquisa", "explora", "entende",
						"questiona", "compreend", "hipotese", "estud", "logia", " conhec", " teoria ", "?" });

		general.put("PENSAMENTO",
				new String[] { "idéia", "pensamento", "filósofo", "filosofia", "pergunta", "explica", "imagina",
						"sonho", "deduz", "criar", "criativ", "fantasi", "realidade", "pensar", "pensamento", "ilusio",
						"ilusor", "ilusao", "acredita", "crenca", " pensador", " pensa ", " sonha", "mental" });
		
		general.put("PLANETA",
				new String[] { " terra ", " marte ", " vênus ", " mercurio ", " plutao ", " netuno ", " saturno ", " urano "
						, " jupiter "});

		general.put("PLANO",
				new String[] { "planejar", "pdca", "organizar", "prever", "medir", "controle", "estratég", "recurso",
						"execucao", "esquema", "reuniao", "planejame", "roadmap", "acompanhamen", "scrum", "burningdown",
						"diagrama de pareto", "project", "relatorio", "orcamento", "budget", "orcado",
						"cumprimento de meta"});
		
		

		general.put("PLANTA",
				new String[] { "planta", "caule", "flor", "folha", "raiz", "semente", "cotiledone", " talo ", " seiva ",
						"clorofila", "cloroplasto", "photosíntese", " pólem ", "poliniz", "enchert", " galho ",
						"espinho", "pétala", "casca", "frut", "adubo", "rosas", "bromeli" });

		general.put("POESIA",
				new String[] { "poesia", "lírico", "poetic", "poetiza", "poeta", "semiologic", "épico", "métrica",
						"liturgi", " hino ", " salmo ", " sura ", "hádice", "gregorian", "retóric", " verso", " prosa ",
						" eloquen", "norma culta", " soneto" });

		general.put("POLUICAO", new String[] { " lixo ", "detrito", "sujeira", "limpesa", " polui", " poluent",
				" refugo", "escória", " recicla", " chorume", "aterro sanitario", "descarte de", "coleta seletiva" });

		general.put("POLITICA",
				new String[] { "sociedade", "politica", "filosof", " lei ", "govern", "partid", "prefeit", " ong",
						"parlament", "candidat", "direito", "eleição", " voto ", "eleitor", " minist", " rei ",
						"rainha", "presidente", "ditador", "ditadura", "despot", "impera", "imperio", " rein", "cidada",
						"social", "sociais", " etica", " etico", "militan", "partido poli", "partidos polit",
						"sindicato", "sindicalis", "conselho de classe", "democra" });

		general.put("PONTOS_CARDEAIS", new String[] { "norte", " sul", "sulista", "leste", " oeste ", " nordeste",
				"nordestin", "sudest", "sudoest", "noroeste", });

		general.put("POSICIONAMENTO",
				new String[] { " perto ", " longe ", " alto ", " baixo ", " ir ", "vir ", " viagem ", " partir ",
						" vertical ", " horizont", "adjace", "adjunt", "esquerd", "direit", "cima", "baixo", "lado",
						" atras ", "proximo", "distante", "dentro", "fora", "intern", "extern" });

		general.put("POVO", new String[] { " povo ", "população", "comunidade", "morador", "habitante", " nação ",
				" raça ", "etni", "etno", "cracia", "pardo", " afro" });

		general.put("POVOS_ANTIGOS",
				new String[] { "grego", "greco", "roman", "nordic", "mesopot", "sumer", "egip", "barbar", "indio" });

		general.put("PINTURA",
				new String[] { "tela", "pintura", "gravura", "desenho", "rabisco", "pintar", "pintura", "tinta",
						"obra de arte", "quadro", "printing", "printer", "blueprint", "fingerprint", "brush", "imagem",
						"imagens", "camera", "pincel", "tingi", "tinta", "pintor" });

		general.put("PRACA", new String[] { "banco da praça", " coreto", "fonte de água", "chafaris", "parques",
				"jardim", "calçada" });

		general.put("PRAGAS", new String[] { "praga", "lavoura", "platio", "cultivo", "transmissão de doença",
				"vetores de doença", "vetor de doenç", "controle de praga", "defensivo", "agrotóxico" });

		general.put("PRISAO",
				new String[] { "preso", "julga", "pena ", "penal", "condena", "presídi", "cadeia", " cela ", "captura",
						"algema", "encarcera", "prision", "penitenci", "delegacia", "detetive", "detetado", " confina",
						"condena", " prender " });

		general.put("PROBLEMAS_HUMANOS", new String[] { "fome", "extinca", "consumis", "guerra", "desigualdade",
				"pobre", "marginal", "polui", "sujeira", "lixo", "degradacao", "contamina", "tolerancia", " paz " });

		general.put("PROPRIDADE", new String[] { "posse", "propriedade", "pertence", "dono", "patrimonio", "recurso",
				"proprietario", "senhor", "possui" });

		general.put("PROGRESSAO",
				new String[] { " prosper", " cresci", " avanç", " cresce", " progress", "retrogra", " atras",
						"em frente", "fortalec", "enriquec", "retard", "diminui", "decai", "retrocesso", "regresso" });

		general.put("PROSTITUICAO",
				new String[] { "prostituta", "gigolo", "iniciacao sexual", "sexo pago", "doencas venerias", "promiscuo",
						"promisquidade", "mesalina", "cortesas", "hetera", "doenca sexual", "sexualmente transmissivel",
						"dst", "turismo sexual", "pedofil", "garotas de programa", "garota de programa",
						"garoto de programa", "garotos de programa", "striptease", " motel", " moteis", "prostibulo",
						"casa noturna", "assedio sexual", "erotismo", "erotic", "sexshopp", "fetiche", "sexo casual",
						"traicao conjugal", "sexo fora do casamento", "travesti", "transformista"});

		general.put("QUANTIDADE", new String[] { "muito", "pouco", "demais", "excesso", "estrapola", "acaba", " falta ",
				"suficiente", "máxim", "mínim", "normali", "equacion", "ajusta", "singul", "plural" });

		general.put("QUALIDADE", new String[] { "bom", "ruim", "eficaz", "satisfatório", "satisfaz", "atende",
				"impressiona", "assusta", " boa ", "péssima", " péssimo ", "satisfeit", "qualidade" });

		general.put("QUIMICA",
				new String[] { "químic", "átomo", "atomi", "elétron", "próton", "mistura", "reação", "composto",
						"ácido ", "ácida ", "acidez", " alcalin", "básico", "eletró", "óxid", "quimica inorganica",
						"quimica organica", "alquimia", "pedra filosofal", "amino", "carbon", "carboidrato", "destila",
						"decanta", " neutron ", " ion " , " cation ", " anion " , " catodo ", " anodo "});

		general.put("REACAO_QUIMICA",
				new String[] { " agente ", " reagente ", "composicao quimica", "molecula", "endotermic", "esotermic",
						"lavoisier", " prost ", " dalton ", " valencia ", "isobaro", "isotopo", "isotono",
						"balanceamento", "decomposicao", "oxireducao", "cataliza", "oxigen", " 1° membro ", " 2° membro " });

		general.put("REDES_SOCIAIS", new String[] { "facebook", "twitter", "linkedin", "instagram", "youtube",
				"snapchat", "orkut", "rede social", "redes sociais", "midias sociais", "midia social", " whatsapp ", " tinder "
				, " skype ", " facebook messenger"});

		general.put("REFEICAO_SUPRIMENTO",
				new String[] { "janta", "almoç", "refei", "comer", "aliment", "comida", "digest", "fritura", "fastfood",
						"lanche", "restaurante", "padaria", "açougue", "lanchonete", "foodtruck", "supermercado",
						" feira", "quitanda", "mercadinho", "peixaria", "frigorifico", "pizzaria", "hamburgueria",
						"dieta" });

		general.put("RELACAO_HUMANA",
				new String[] { "amizade", "paixão", " amor", " ódio", " invej", " namor", " ciume", " empati", " união",
						" afetiv", " afeto", " poder", "relaç", "amigo", "amiga", "esposa", "marido", "divorci",
						" desquit", "uniao estavel", "comunhao de bens", "comunhao parcial de bens", "gamia " });

		general.put("RELIGIAO",
				new String[] { " deus", " fé ", "devoção", "devot", "pecado", "espirito", "messias", "salvador",
						" alma ", "religi", "crença", "protestant", "cristian", "maometis", "uband", "budis", "xitois",
						"islamis", "sagrado", " sacro", " sacra", "calice", " culto", " cultu" });

		general.put("REINOS_ANIMAIS", new String[] { " animalia", " protista", " monera", "fungi", "plantae",
				"carnivoro", "onivoro", "reino animal" });
		
		general.put("RURAL", new String[] { " campo", "fazenda", "sítio", "gado", "rebanho", "interior", "chácara",
				"cercado", "ribeir", "campestre", "rural", "vaqueiro", "vaquejada", "cavalo", "carroça", "engenho",
				"cercado", "curral", "rebanho", "estrada de barro", "jumento", "burro", " asno ", "calinha", "frango",
				"granja", "cocheira", "pilao", "moinho", "vacaria", "boiadeir", " peao ", "pocilga", " roça", "pomar",
				" orta ", "canavial", "colheita", "safra", "plantio", "estacao das chuvas", "festa junina",
				"agricultor", "caipira", "trator", " arado", "enchada", " pá ", "ciscador", " foice ", " machado ",
				"motoserra", " viola", "sertao", "sertanejo", "chapeu de palha", "Jibao", " arreio", " chicote",
				"chibata", "colhedeira", "agranomo", "cooperativa", " rodeio" });

		general.put("SAUDE", new String[] { "saúde", "saudável", "curado", "forte", "bem-estar", "vacina", "robust",
				"medic", "enfermeir" });

		general.put("SEGURANCA", new String[] { "segurança", "proteção", " arma ", "assalt", "política pública",
				"ir e vir", "polícia", "latro", "furto", "roubo", "rouba", "belico" });

		general.put("SEGURANCA_INFORMACAO",
				new String[] { "senha", "password", "criptogra", "certificado", "cripta", "ataque", "roubo de",
						"engenharia social", "autentica", "protocolo de segurança", "antivrirus", "spam", "auth",
						" jwt ", "certificad" });

		general.put("SEXO",
				new String[] { "reprodução", "reprodut", "sexo", " coito", "relação sexual", "acasala", "cortejo",
						"macho", "fêmea", "gestação", "gravides", " gravida ", " prazer", " fornica", " mascul", " femini", " parto ",
						" nascimento ", "fecunda", "fecundo", " ovulo", "espermatozoide", " feto", "hermafrodita",
						"abort", "sexua", " engravidar " , " engravid" });

		general.put("SELECAO", new String[] { "escolha", "filtr", " selec", " selet", " individu", " descart",
				" despres", " separa", " catalog" });

		general.put("PERIODICIDADE",
				new String[] { "segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo", "dominical",
						"semanal", " diário ", "mensal", "periódico", "vespertino", "horario", "noturn", "diurn",
						"repetitiv", "manhã", "tarde", "noite", "madrugada", "alvorada", "alvorece", "janeiro",
						"fevereiro", " março ", " abril ", " maio ", "junho", "julho", "agosto", "setembro", "outubro",
						"novembro", "dezembro", " rotin", "aleatori", "period" });

		general.put("SENTIDOS", new String[] { "audição", "olfato", "paladar", " tato ", "visão", "ouvir", "olhar",
				"cheir", "tocar", "ouvido", " pele ", "degust", "retina" });

		general.put("SENTIMENTO",
				new String[] { "medo", "raiva", "alegria", "frustra", "eufori", "pavor", "esperanç", "afeto", "afeti",
						"afeic", "apego", "carinho", "despres", "depress", "desist", "trist", "ódio", "fobia", "averss",
						"Amor", "feliz", "bondos", "benig", "malign", "sentimento", "sentir", " emocao ", "emocion" });

		general.put("SISTEMA_BUSCA",
				new String[] { "google", "yahoo", "altavista", "motor de busca", "indexação", "noSQL", "grafo", "busca",
						"pesquisa", "indexa", "bigquery", "hype ", "solr ", "LUCENE", "weekpedia", "buscador",
						"motor de pesquisa", "sistema de busca", " bing ", "Lycos", " A9 ", "web crawler", " FTP ",
						"bibliotecono", " SIRI ", "recuperacao da informacao", "Research", "Analysis", " cortana " });

		general.put("SISTEMA_OPERACIONAL",
				new String[] { "linux", "windows", "macos", "solaris", "fucshia", "android", " IOS ", " badaos ",
						" Operating System", "sistema operativo", "sistema operacional", "sistema de arquivo", " ROM ",
						" BIOS ", "arquitetura RISK", "arquitetura SISC", " kernel ", "monotarefa", " multitarefa ",
						" pipes ", "socket", "soquete", " ULA ", "unidade logica e aritmetica", "Swapp",
						"processamente em lote", "real time", "tempo real", "MS-DOS", "FreeBSD", "OS/2" });

		general.put("SISTEMA_SOCIAL", new String[] { "capitalis", "comunis", "nazis", "facis", "feudalis", "escravis",
				"asiático", "socialis", "primitivo", "mercantil", });

		general.put("SOFTWARE", new String[] {"software", "teste de software", "mobile",   "tecnologia de informação",
				"programa de computador", "aplicativo", "modelagem de software", "virtual",  "arquivo",
				 "app ",  "relacional", "development", "developer", });
		
		
		
	general.put("SOM",
				new String[] { "onda sonora", "sonoridade", "barrulh", "falante", "fones", "sonar", "morcego", " eco ",
						"reverbe", "grave", "agudo", "timbre", "distorção", "mixagem", "masteriza", "equaliza",
						"compress", "amplifica", " sinal ", "ruido", "silenci", "calar", "calado", " mudo ", " surdo ",
						"surdez" });

		general.put("SUBSTANCIAS",
				new String[] { "proteina", "ácido nucleico", "citosi", "guanina", "uracil", "meiose", "osmose",
						"osmótic", "linfocito", "leucocito", "hemacea", "hemoglobina", "vitamina", " salga", "salino",
						"salini", "salina", " sal ", });

		general.put("CAPACIDADE",
				new String[] { "diferencia", "espontâne", "prodige", "gênio", "genial", "capacidade", "facilidade",
						" nato ", " inato ", "expert", "competen", "qualifica", "cursar", "perito", "pericia", "habil",
						"experiente", "experiencia", "aperfeicoa", "competente", "desempenho", " craque " });

		general.put("TECNOLOGIA", new String[] { "tecnologi", "foguete", "espacial", " nasa ", "celular",
				"desenvolvimento", "técnica", " novo ", "inova", "metodo", "bluetooth" });

		general.put("TEATRO", new String[] { "drama", "comédia", "teatro", "palco", "peça teatral", "musical", "ópera",
				"roteiro", "interpret", "atriz", " ator", " cena ", " cenário", " cenic", "persona", "personif" });

		general.put("TELA_COMPUTADOR", new String[] { "pixel", " png ", "bitmap", "JPEG", "BMP", "resolução",
				"densidade", "layer", "RGB", "tela plana", "cromati", " tela ", " led ", "cristal liquid" });

		general.put("TELECOMUNICACAO", new String[] { "satélite", "telefone", "telegrafo", "onda eletromagnetica",
				"amplitude modulada", "frequencia", "transmiss", "transmit", "converss", "tcp/ip", "telecomunicac" });

		general.put("TEMPO",
				new String[] { "semana", "mês", "ano ", "década", "século", "passado", "presente", "futuro", "bimestr",
						"trimestr", "semestr", "anual", "bienal", "contemporâ", "anterior", "posterior", "agora",
						"depois", "efemero", "volatil", "rapido", "fugaz", "fugacid" });

		general.put("TERRENO",
				new String[] { "areia", "terra", "rocha", "rochos", "sediment", "terreno", "geólogo", "geologi",
						"relevo", "planalto", "montanha", "planície", "petro", "petri", "areno", "lama", "pantano",
						" barro ", "saibro", "argila", "argilos", "quartzo", "bauxita", " caverna " });
		general.put("TORAX", new String[] { " seio ", " mama ", "pulmão", "torax", "costas", "homoplata", "ombro",
				"peito", "pescoço" });

		general.put("TRABALHO", new String[] { "trabalho", "atividade", "acao" });

		general.put("TRANSPORTE",
				new String[] { "carro", "bicicl", "transport", "ferrovia", "marítimo", "aéreo", "nave", "naval",
						"terrestre", "submarino", "barco", " bote ", "jangad", "caravela", "avião", "aeronave",
						"helicóptero", " jato", "motorista", "piloto", "condutor", "aeroeapcial", "veículo", "rota",
						"caminho", "trajeto", "viaj", "percurso", "corrida", " transpor ", "ultrapassa", "transit" });

		general.put("TRANSPORTE_PUBLICO", new String[] { "metrô", "ônibus", "trem", "caminhão", "pau de arara",
				"lotação", "balsa", "rodovia", "estrada", "ponte", "túnel" });

		general.put("UNIDADES_MEDIDA",
				new String[] { "m/s", "km/h", "kg", "m2", "m3", " wh ", " ah ", " hp ", "btu", "metro", "polegada",
						"litro", " quilo", " mega", " nano", " pico", " zeta", " mili", " micro", "macro", "/ml", "%",
						"mg/kg", "mg/l", "lpsf/gq", "d+lps=", "d/h" });
		general.put("UNIVERSO",
				new String[] { "univers", "matéria", "dimens", "big bang", "matéria escura", "relativ", "quantic" });

		general.put("URBANO", new String[] { "urbano", "urbanid", "espaços", "arquitet", "urbaniza", "cidade projetada",
				"cidade inteligente", "licenciamento", "paisagis", "revitaliz", "suburb" });

		general.put("UTENSILIOS",
				new String[] { " pote ", " copo ", "canec", "xicara", "balde", "barril", "tonel ", "frasco", " tubo ",
						"kitassato", "instrumentacao", "bico de bunsen", "recipiente", " garfo ", " faca ", " colher ",
						" talher", " pires ", " louça ", "bandeja", "escorredor", "panela", "frigideira", "calice", " objeto "
						, " ferramentas "," ferramenta " });

		general.put("VALORES",
				new String[] { "valor", "ética", "moral", "princípio", "convic", "virtu", "hábito", "vício",
						"etica profissional", "honest", "corrup", "desvio de verba", "desvio de campanha",
						"propina", "escandalo", "ilicitu", " etico", "cartel", "carteis", "trust", "corromp", 
						"fraud", "compra de voto", "venda de voto", " multa", "entidade", "instituic"});

		general.put("VEGETACAO", new String[] { "floresta", "caatinga", "cerrado", "tundra", "savana", "taiga",
				"xerofit", "mangue", "pantan", "botânic", "árvore", "samambaia", " alga ", " mata ", "vegeta" });

		general.put("VENTO", new String[] { " vento ", "tornado", "furacão", "tormenta", " brisa ", "ciclone", " ventania " 
				," maresia ", " eolo ", " ar ", " moncao " , " minuano ", " vendaval ", " ventaneira ", " soprador "});

		general.put("VERDADE",
				new String[] { "certificado", "auth", "credencia", "certidão", "cartório", "tabeli", "veridic",
						" puro ", "pureza", "mentira", "falso", "falsi", "engana", "engano", "equivoc", "histeri",
						"art. 171", "verific", "pseudo" });

		general.put("VESTUARIO",
				new String[] { "camisa", "shorts", "sapat", "tênis", "cueca", "calça", "sutiã", "chapéu", "boné",
						" touca ", " meia ", "sandália", "camiseta", " terno ", "gravata", " meias ", "vesturario",
						"roupas", "moda", "tecido", "mochila", "algodao", " seda ", "couro", "roupa sintetica",
						"constur", "adorno", "veste", "vesturario", "vestiment", "camufla", "joias", "malhas" });

		general.put("VIA_PUBLICA",
				new String[] { "asfalto", "iluminação", "poste", "abrigo", "parada", "ponto de ", " via ", "semáforo",
						"rede elétrica", "distribuição", "lampada", "corredor de", "táxi", "viaduto", "praça" });

		general.put("VIDA_FASE",
				new String[] { " ovo ", " larva", " pupa ", "criança", "infant", "jovem", "adulto", "adulta", "juven",
						"idoso", "senil", "idosa", "idade", "bêbe", "recem nasc", "puberdade", "adolesc", "nascimento",
						"nasce", "crescimento", "morte", "sepult", "enterro", "enterra", " cremar ", "cremacao",
						" falec", "falenci", " vitali", "amadurec", "envelhec", " matura" });

		general.put("VIDA_GENERO",
				new String[] { "homem", "homens", "mulher", "menino", "menina", "rapaz", "moça", "hosexual", " lesbi",
						"maxismo", "maxista", "feminismo", "feminista", "identidade de genero", "masculin", "femini" });

		general.put("VIGILANCIA",
				new String[] { " vigia", " camera", " monitora", " alarm", "carro forte", "segurança patrimo",
						"vigilan", " olhar ", "observa", " confere", "conferen", "guarda", "seguranca", "inspecio",
						"inspecao" });

		general.put("VIOLENCIA",
				new String[] { " briga", "raiva", "agreção", "agredir", "agress", "queixa", "violên",
						"confront", "espanca", "bater", " soco ", "chute", " golpe", " socar ", " pancada ", "matar",
						"assassina", "estupr", "viola", "bulling", "forca fisica", "forca bruta", "jiujtsu",
						"karate","muay thai", " judo", " sumo", " boxe", "taekwondo", "cotovelada", "armlock",
						"leglock", "mata leao", "ponta pe", "paulada", "acao contundente", "esfaque", "facada",
						"queixa policial", "boletim de ocorrencia", "disk denuncia", "conflito armado", "bala perdida",
						"troca de tiro", "maria da penha", "homicidio", "feminicidio", "suicidio", "crime violento",
						"crime barbaro", "trafico", "uso de droga", "ilegal", "imoral", "gangue", "quadrilha", 
						"crime organizado", "traficante", "trafico", "milicia", "cartel", "comando vermelho",
						"faccao", "faccoes", "grupo de extermineo", "grupos de extermineo"});

		File csvFile = new File("res/bdtd-doc-traineset.csv");
		CSVLoader csv = new CSVLoader();
		csv.setSource(csvFile);
		csv.setFieldSeparator(";");

		FileWriter writer = new FileWriter(new File(csvFile.getAbsolutePath().replace(".csv", "-concepts.csv")));

		Map<String, Integer> map = new TreeMap<String, Integer>();
		String header = "\"id\";\"qui_exp\";\"tem_data\"";
		for (String item : general.keySet()) {
			header += ";\"" + item.toLowerCase() + "\"";
		}
		header += ";\"clazz-label\"";

		writer.write(header);
		writer.write('\n');

		for (Instance instance : csv.getDataSet()) {
			try {
				StringBuffer line = new StringBuffer();
				line.append(instance.value(0));

				String txt = instance.stringValue(2).trim().toLowerCase();

				txt = unaccent(txt);
				txt = limpar(txt);
				String copia = txt;

				if (hasChemicalFormula(txt)) {
					line.append(";1");
					String classe = instance.stringValue(4) + "quim";
					if (!map.containsKey(classe)) {
						map.put(classe, 0);
					}
					map.put(classe, map.get(classe) + 1);
				} else {
					line.append(";0");
				}

				if (hasData(txt)) {
					line.append(";1");
					String classe = instance.stringValue(4) + "form";
					if (!map.containsKey(classe)) {
						map.put(classe, 0);
					}
					map.put(classe, map.get(classe) + 1);
				} else {
					line.append(";0");
				}

				Iterator<String> generalIterator = general.keySet().iterator();
				while (generalIterator.hasNext()) {
					String key = generalIterator.next();

					key = unaccent(key);

					boolean hasConcept = false;
					int countThe = 0;

					for (String value : general.get(key)) {
						value = unaccent(value);

						if (txt.contains(value)) {

							countThe += StringUtils.countMatches(txt, value);

							hasConcept = true;
							String classe = instance.stringValue(4) + key;

							if (!map.containsKey(classe)) {
								map.put(classe, 0);
							}
							map.put(classe, map.get(classe) + 1);

							copia = copia.replace(value, " **");
						}
					}
					if (hasConcept) {
						line.append(";" + countThe);
//						line.append(";1");
					} else {
						line.append(";0");
					}
				}
				line.append(";\"" + instance.stringValue(4) + "\"");

				if (!txt.isEmpty()) {
					for (String newWord : copia.split("[ .\\-,;:'()]")) {
						if (newWord.contains("*"))
							continue;
						newWord = newWord.trim();
						if (!newWords.containsKey(newWord)) {
							newWords.put(newWord, 0);
						}
						newWords.put(newWord, newWords.get(newWord) + 1);
					}
				}

				writer.write(line.toString());
				writer.write('\n');

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		writer.flush();
		writer.close();

		Map<Integer, TreeSet<String>> newWordsWty = new TreeMap<Integer, TreeSet<String>>();
		for (String key : newWords.keySet()) {
			int qtd = newWords.get(key);
			if (!newWordsWty.containsKey(qtd)) {
				newWordsWty.put(qtd, new TreeSet<String>());
			}
			newWordsWty.get(qtd).add(key);
		}

		System.out.println(newWordsWty);

		System.out.println("FIM");
	}

	public static String limpar(String txt) {

		txt = txt.replace("este trabalho", " ");
		txt = txt.replace("esta dissertacao", " ");
		txt = txt.replace("esta monografia", " ");

		txt = txt.replace("presente trabalho", " ");
		txt = txt.replace("presente dissertacao", " ");
		txt = txt.replace("presente monografia", " ");

		return txt;
	}

	public static String unaccent(String src) {
		return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static boolean hasData(String text) {
		String regex = "[0-3][0-9](\\/[0-3][0-9])*(\\/[0-9]{4}|\\/[0-9]{2})";
		for (String word : text.split(" ")) {
			Matcher matcher = Pattern.compile(regex).matcher(word.replace(" ", ""));
			boolean result = matcher.find();
			if (result) {
//				System.out.println("["+word+"] ");
				return result;
			}
		}
		return false;
	}

	public static boolean hasChemicalFormula(String text) {
		text = text.toLowerCase();
		String[] words = new String[] { "Ac", "Ag", "Al", "Am", "Ar", "As", "At", "Au", "B", "Ba", "Be", "Bh", "Bi",
				"Bk", "Br", "C", "Ca", "Cd", "Ce", "Cf", "Cl", "Cm", "Co", "Cr", "Cs", "Cu", "Ds", "Db", "Dy", "Er",
				"Es", "Eu", "F", "Fe", "Fm", "Fr", "Ga", "Gd", "Ge", "H", "He", "Hf", "Hg", "Ho", "Hs", "I", "In", "Ir",
				"K", "Kr", "La", "Li", "Lr", "Lu", "Md", "Mg", "Mn", "Mo", "Mt", "N", "Na", "Nb", "Nd", "Ne", "Ni",
				"No", "Np", "O", "Os", "P", "Pa", "Pb", "Pd", "Pm", "Po", "Pr", "Pt", "Pu", "Ra", "Rb", "Re", "Rf",
				"Rg", "Rh", "Rn", "Ru", "S", "Sb", "Sc", "Se", "Sg", "Si", "Sm", "Sn", "Sr", "Ta", "Tb", "Tc", "Te",
				"Th", "Ti", "Tl", "Tm", "U", "V", "W", "Xe", "Y", "Yb", "Zn", "Zr" };

		StringBuilder strChemical = new StringBuilder();
		for (String word : words) {
			word = unaccent(word);
			if (strChemical.length() > 0) {
				strChemical.append("|");
			}
			strChemical.append(word + "[0-9]\\w*");
		}
		String regex = strChemical.toString().toLowerCase();
		Matcher matcher = Pattern.compile(regex).matcher(text);
		boolean result = matcher.find();

//		if (result) {
//			int start = matcher.start();
//			int end = matcher.end();
//			System.out.print("["+text.substring(start, end)+"] ");
//		} 		
		return result;

	}
}
