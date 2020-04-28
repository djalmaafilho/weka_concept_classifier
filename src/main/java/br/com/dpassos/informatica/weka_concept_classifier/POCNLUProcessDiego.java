package br.com.dpassos.informatica.weka_concept_classifier;

import java.io.File;
import java.io.FileWriter;
import java.text.Normalizer;
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

		general.put("ACESSO", new String[] { " acess", " entrada", "entrar", "entrou", " entra ", "entrando", " saída",
				" porta ", "portal", "portaria", "portão", "gateway", " bloque", " chave", " fechadura", "abertura",
				"impedimento", " barrar", "barrado", "barrou", "liberdade", "credencia", "adentrar", " acercamento " });

		general.put("AGRUPAMENTO",
				new String[] { "coleção", "conjunto", "grupo", "equipe", " unir ", "contingente", "coletivo", "união ",
						"multirão", " tropa ", " extrato", "extrati", "colegiado", "agrupa", "coleções", "coleciona",
						"juntar", " ajuntar ", " associação ", " partido ", " facção ", "acopla" });

		general.put("AJUDA", new String[] { " auxíl", "ajuda", "socorr", "suporte", "help", "assiste", "assisti",
				"servir", "colabora", " amparo ", " benefic ", " caridad " });

		general.put("AGUA_PROXIMIDADE", new String[] { " foz ", "ribeir", "litorâ", "praia", "costa", "delta", "húmus",
				"oásis", "beira mar", "praie", " duna ", " dunas " });

		general.put("AQUATICO",
				new String[] { " rio ", " rios ", "riacho", " mar ", "oceano", " lago ", " lagoa ", " marinh",
						" marítm", "afluente", "efluente", "nascente", "córrego", "abissal", "barragem", " represa ",
						"irriga", "reservatorio", "vazao", "vazamen", "bacias", "hidrografi", " aquoso ", " hídrico ",
						" aquática ", " aquático ", "hidro" });

		general.put("ANALISE_CLINICA",
				new String[] { "clinico", "exames", "seringa", "agulha", "prontuário", "material coletado",
						"diagnostico médico", "proagnostico", "laudo clinico", "relatorio médico", "posto de sau",
						"clinica ", "analise clinica", "soropositiv", "soronegativ", "raio-x", "diagnostic" });

		general.put("ANALISE_LABORATORIO",
				new String[] { "mielograma", "ressonância", "leucograma", "hemograma", "hematocrito", "bacterioscopia",
						"antibiograma", "coprocultura", "micológico", "eritrograma", "morfologia das hemácias" });

		general.put("ANATOMIA_HUMANA", new String[] { "Abdômen", "Amígdala", "Alvéolo pulmonar", "Antebraço", "Ânus ",
				"Apêndice ", "Ápice da bexiga Aponeurose Aracnoide", "Artérias ", "Artéria Aorta", "Artéria axilar",
				" Artéria cerebral anterior", " Artéria cerebral média", " Artéria cerebral posterior ",
				"Artérias coronárias", " Artéria coronária direita", " Artéria coronária esquerda",
				" Artéria esplênica", " Artéria lingual", " Artéria profunda do pênis", "Artéria pulmonar",
				"Artéria renal", "Artéria subclávia", " Artéria suprarrenal inferior", " Artéria suprarrenal média",
				" Artéria suprarrenal superior ", "Artéria vertebral Arteríolas", "Arteríola aferente",
				"Arteríola eferente", "Articulações Aurículas", "Aurícula direita", "Aurícula esquerda", "Axilas Bacia",
				"Baço", "Barba", "Barriga", "Barriga da perna", " Bexiga  ", "Bigode Boca ", "Bochechas", "Braços",
				"Brônquios", "Bronquíolos", "Bulbo raquidiano", "Cabelo", "Cabeça", "Caixa torácica Calcanhar",
				"Cálice maior", "Cálice menor", "Canal auditivo", " Canais semicirculares", "Canela",
				"Capilares linfáticos", " Capilares sanguíneos", "Cápsula articular", " Cápsula de Bowman",
				"Cápsula renal", "Cárdia", "Ceco", "Cego", "Cerebelo ", "Cérebro", "Hemisfério direito",
				"Hemisfério esquerdo", "Cérvix", "Cílios", "Cintura", "Clítoris", "Cóclea", "Cólon ",
				"Cólon ascendente", "Cólon descendente", "Cólon sigmoide", "Cólon transverso", "Coluna vertebral ",
				"Coração ", "Cordas vocais Córnea", "Corpo ciliar", "Corpúsculo renal", "Córtex cerebral",
				"Córtex renal", "Costas", "Cotovelo ", "Coxa", "Couro cabeludo Cristalino", "Dedos",
				"Dedos da mão Dedos do pé Dentes ", "Caninos", "Incisivos", "Molares", "Pré-molares", "Derme",
				"Diafragma", "Dorsal", "Dorso da mão Ducto cístico", "Ducto colédoco", "Ducto deferente",
				"Ducto ejaculatório", " Ducto hepático comum", "Duodeno", "Dura-máter", "Endocárdio", "Esfíncter",
				"Esclera", "Escroto ", "Esqueleto", "Estômago", "Esófago ", "Epiderme ", "Epidídimo", "Epiglote",
				"Exoderme Falanges", "Falanges da mão", " Falanges do pé ", "Face", "Faringe  ", "Fossas nasais",
				"Fêmures", "Fídago ", "Folículo piloso", "Fóvea", "Frênulo da língua", " Frênulo prepucial Garganta",
				"Gengivas", "Glabela Glande", "Glote", " Glândulas", " Glândula endócrina", " Glândula gástrica",
				" Glândulas salivares", "Glândula salivar menor", "Glândula sebácea", "Glândula sudorípara",
				"Glândula suprarrenal", "Gordura Hélice", "Hilo pulmonar ", "Hilo renal", "Hímen", "Hipocampo ",
				"Hipoderme", "Hipófise", "Hipotálamo ", "Humor aquoso", "Humor vítreo", "Íleo ", "Intestino delgado ",
				"Intestino grosso ", "Íris ", "Jejuno ", "Joelho", "Lábio inferior", "Lábio superior", "Lábios",
				"Grande lábio", "Pequeno lábio", "Laringe ", "Laringofaringe", "Ligamentos", "Ligamento amarelo",
				" Ligamento cruzado anterior", "Ligamento cruzado posterior", "Língua", "Linfonodo", "Lobos",
				"Lobo inferior", "Lobo médio", "Lobo superior", "Lóbulo da orelha ", "Mamas", "Seios", "Mamilos",
				"Mãos", "Meato nasal Meato ureteral Medula espinhal", "Medula óssea", "Medula renal", "Meninge",
				"Meniscos", "Menisco lateral", "Menisco medial", "Mesentério Metarteríola Microvilosidades",
				"Miocárdio ", "Músculos", "Nádegas", "Narinas", "Nariz", "Nasofaringe ", "Nervos", "Nervos axilares",
				" Nervos cranianos", "Nervos espinhais", "Nervos ópticos", "Nervos vestibulococleares", "Nuca", "Olhos",
				"Ombros", "Orelhas", "Ouvidos ", "Ouvido externo", "Ouvido interno", "Ouvido médio",
				"Orifício interno da uretra", "Orifício atrioventricular direito", "Orifício atrioventricular esquerdo",
				"Orofaringe ", "Ossos", "Ovários ", "Palato", "Palato duro", "Palato mole", "Palmas", "Pálpebras",
				"Pâncreas", "Panturrilha Peito", "Peito do pé Pele", "Pelos", "Pelos abdominais", " Pelos faciais",
				"Pelos peitorais", " Pelos púbicos ", "Pelve renal", "Pênis ", "Pericárdio", "Períneo ", "Periocular ",
				"Periósteo ", "Pernas", "Pés", "Pescoço", "Pestanas", " Pia-máter", "Piloro", "Pituitária",
				" Placentas", " Planta do pé", "Pleura ", "Pomo de adão ", "Ponto cego", " Prepúcio", "Próstata ",
				"Pulmões ", "Pulmão direito", " Pulmão esquerdo", " Punho", "Pulso", "Pupilas ", "Queixo",
				"Quiasma óptico", " Rafe perineal ", "Retinas", "Reto ", "Rins ", "Rosto", "Seio coronário",
				" Seio renal", " Septo atrioventricular", "Septo interatrial", "Septo interventricular",
				"Septo nasal Sobrancelhas", "Tálamo", "Tecidos", " Tecido muscular", " Tecido nervoso", "Tecido ósseo",
				" Tendões", "Tendão calcâneo ", " Testa ", "Testículos ", "Tímpano ", "Tiroide", "Tonsila", "Tórax",
				"Tornozelo", "Torso", "Traqueia ", "Trígono anal", " Trígono da bexiga", " Trígono femoral", " Tronco",
				"Tronco cerebral ", "Trompas de Falópio", "Tuba auditiva", "Umbigo", " Unhas ", "Uretra  ",
				"Uretra esponjosa", "Uretra membranosa", "Uretra pré-prostática ", "Uretra prostática", "Ureteres ",
				"Útero ", "Utrículo prostático", " Úvea ", "Úvula", "Vagina ", "Válvulas cardíacas", "Válvula aórtica",
				"Válvula do seio coronário", " Válvula mitral", "Válvula pulmonar", "Válvula tricúspide",
				"Válvula conivente", " Vasos linfáticos", "Vasos sanguíneos ", "Veias ", "Veia axilar", " Veias cava",
				" Veia cava inferior", " Veia cava superior", "Veias cerebrais inferiores", " Veia cerebral média",
				" Veias cerebrais superiores", " Veia coronária", " Veia dorsal profunda do pênis", "Veia esplênica ",
				"Veias linguais", " Veia pulmonar", "Veia renal", "Veia subclávia", " Veia suprarrenal",
				" Veia vertebral", " Ventrículos", "Ventrículo direito", "Ventrículo esquerdo", "Vénulas",
				"Vilosidades intestinais", "Virilha", "Vesícula biliar ", "Vesícula seminal", "Vulva", "barba ",
				"bigode", " lingua ", "faring", "laging", "otorrin", " derma", "cilio", });

		general.put("ANIMAIS", new String[] { "animais", "bicho", " fera ", " besta ", " ave ", "pássaro", "peixe",
				"anfíbio", "réptil", " zoo", "orgânic", "rinoceronte", "cavalo", "girafa", "hipopótamo", "touro",
				"tigre", " vaca ", " boi ", " gado ", "bufalo", " porco", "suino", "suina", "equino", "equina",
				"cetaceos", "cetacea", " bestial ", " animalidade ", "animal", "baleia", " cão ", " gato ", "tubarão",
				"camelo", "abutre", "ácaro", "águia", "albatroz", " alce ", "alpaca", "anaconda", "anchova",
				"andorinha", "anta", "antílope", "aranha", " arara ", " asno ", " atum ", "avestruz", "babuíno",
				"bacalhau", "badejo", "bagre", "baiacu", "barata", "barbo", "barracuda", "beija-flor", "bem-te-vi",
				"bezerro", "bicho-da-seda", "bisonte", " bode ", " boto ", "búfalo", "burro", "cabra", "cachalote",
				"cachorro", "cágado", "camaleão", "camarão", " camelo ", "camundongo", "canário", "canguru", "capivara",
				"caranguejo", "carneiro", "carrapato", "castor", "cavalo-marinho", "cegonha", "centopeia", "chimpanzé",
				"chinchila", "chita", "cigarra", "cisne", "coala", " cobra ", "codorna", "coelho", "coiote", "coruja",
				"corvo", "crocodilo", "cupim", "cutia", "damão", "dançador", "degolado", " degu ", "diablotim",
				"diabo-da-tasmânia", "diamante-de-gould", "dingo", "dinossauro", "dodô", "doninha", "dragão-de-komodo",
				"dragão-do-banhado", "dragão-voador", "dromedário", "dugongo", "égua", "elefante", "elefante-marinho",
				"ema", "enchova", "enferrujado", "enguia", "enho", "escaravelho", "escorpião", "escrevedeira",
				"esmerilhão", "espadarte", "esponja", "esquilo", "estorninho", "estrela-do-mar", "esturjão", "faisão",
				"falcão", "ferreirinho", "flamingo", " foca ", " fossa ", "fraca-da-guiné", "frango-d'água",
				"freirinha", "fuinha", "furão", "gafanhoto", "gaivota", " galo ", " gambá ", " gamo ", " ganso ",
				" garça ", "garoupa", "gavião", "gazela", "geco", "gerbo", "gibão", "girino", "gnu", "golfinho",
				"gorila", "gralha", "grifo", "grilo", "grou", "guará", "guaxinim", "hadoque", "hamster", "harpia",
				"hiena", "hírax", "iaque", "íbex", " íbis ", "iguana", "iguanara", "impala", "indicador", "indri",
				"inhacoso", "inhala", "inhambu", "irapuã", "irara", "iratim", "itapema", "jabiru", "jabuti", "jaçanã",
				"jacaré", "jacu", "jacupará", "jaguar", "jamanta", "jararaca", "javali", "jegue", "joaninha",
				"joão-de-barro", "jumento", "kakapo", "kea", "kinguio", "koala", "kookaburra", "kowari", "krill",
				" kudu ", "lacraia", "lagarta", "lagartixa", "lagarto", "lagosta", "lampreia", "lavadeira", "lavagante",
				" leão ", "leão-marinho", "lebre", "lêmure", "leopardo", "lesma", "lhama", "libélula", "lince",
				"linguado", "lobo", "lombriga", "lontra", "lula", "macaco", "mamute", "manatim", "mandril", "mangangá",
				"maracanã", "marimbondo", "mariposa", "marisco", "marmota", "marreco", "marta", "massarongo", "medusa",
				"melro", "mergulhão", "merluza", "mexilhão", " mico ", "milhafre", "mineirinho", "minhoca", " mocho ",
				" mono ", "morcego", "moreia", "morsa", " mula ", "musaranho", " naja ", "nambu", "nandaia", "narceja",
				"narval", "náutilo", "negrinho", " neon ", "nhacundá", "nhandiá", "niala", "niquim", "noitibó",
				"numbat", "nútria", "ocapi", "olho-de-boi", " onça ", "orangotango", " orca ", " órix ", "ornitorrinco",
				" osga ", " ostra ", "otária", "ouriço", "ouriço-do-mar", "ovelha", " panda ", "pantera", "papagaio",
				"pardal", " pato ", "pavão", "peixe-boi", "pelicano", "percevejo", "perereca", "periquito", " peru ",
				"pescada", "pica-pau", "pinguim", "piolho", "piranha", "pirarucu", "pombo", "pônei", "porco",
				"porco-espinho", "porquinho-da-índia", " preá ", "preguiça", "pulga", " quati ", "quatimirim",
				"quatipuru", "quebra-nozes", "quebra-ossos", "queixada", "quem-te-vestiu", "quero-quero", "quetzal",
				"quiriquiri", " rã ", "rabudinho", " raia ", "raposa", "ratazana", "rato", "rêmora", " rena ",
				"rendeira", "robalo", "rouxinol", "sabiá", "sagui", "salamandra", "salmão", "sanguessuga", " sapo ",
				"sardão", "sardinha", " saúva ", "seriema", "serpente", "siri", "suçuarana", "suiriri", "suricate",
				"surubi", "surucucu", "tainha", "tamanduá", "tamboril", "tapir", "tarântula", "tartaruga", " tatu ",
				"tatuí", "tentilhão", "tetra", "texugo", "tico-tico", "tilápia", "tordo", "tororó", "toupeira",
				"tritão", "truta", "tucano", "tucuxi", "uacari", "unau", "unha-longa", "unicórnio", "ursopanda",
				" urso ", " uru ", "urubu", "urutaurana", "vaca-marinha", "vaga-lume", "veado", "verdilhão", "verdinho",
				"vespa", "víbora", "vieira", "vira-bosta", "vison", "vitela", "viúva", "viúva-negra", "wallaby",
				"weimaraner", "wombat", "xajá", "xangó", "xará", "xarelete", "xaréu", "xaru", "xauim", " xero ",
				"xexéu", "ximango", "ximburé", "xixarro", "xofrango", "xuri", "yak", "ynambu", "yorkshire", "zabelê",
				"zangão", "zaragateiro", " zarro ", "zebra", " zebu ", "zidedê", "zombeteiro", "zorrilho",
				"mastodonte" });

		general.put("ANTROPOLOGIA",
				new String[] { "hominideo", "antropo", "homo-sapiens", "anthropos", " human", "preconceito",
						"etnocentrismo", "sociologia", "psicologia", "linguist", "etnolog", "direitos humanos",
						"holocausto", "separatis", "xenofob", " ego" });

		general.put("ARQUITETURA",
				new String[] { "arquitetura", " casa ", "predio", "edifício", "pirâmide", "zigurati", "palácio",
						"sobrado", "catedral", "monastério", "arranha céu", "castelo", "fortaleza", " forte de ",
						"templo", " cabana ", " oca ", " obra ", " constru ", "arquitetônico" });

		general.put("ARTE",
				new String[] { " arte ", "musica", "pintura", " dança", " canto", "escultura", "teatro", "cinema",
						"literatura", "espetáculo", "show", "artista", " ator ", " atriz ", " cantor ", " interpret",
						"expressao", " beleza ", " engenho " });

		general.put("BACTERIAS", new String[] { "Acetobacter aurantius", "Acinetobacter baumannii",
				"Actinomyces Israelii", "Agrobacterium radiobacter", "Agrobacterium tumefaciens",
				"Azotobacter vinelandii", "Anaplasma phagocytophilum", "Bacillus", "Bacteroides", "Bartonella",
				"Bordetella", "Borrelia burgdorferi", "Brucella", "Burkholderia", "Chlamydia trachomatis",
				"Calymmatobacterium granulomatis", "Campylobacter", "Chlamydia", "Chlamydophila", "Clostridium",
				"Corynebacterium", "Coxiella burnetii", "Ehrlichia chaffeensis", "Enterobacter cloacae", "Enterococcus",
				"Escherichia coli", "Francisella tularensis", "Fusobacterium nucleatum", "Gardnerella vaginalis",
				"Haemophilus", "Helicobacter pylori", "Klebsiella pneumoniae", "Lactobacillus",
				"Legionella pneumophila", "Leptospira", "Listeria monocytogenes", "Microbacterium multiforme",
				"Micrococcus luteus", "Moraxella catarrhalis", "Mycobacterium", "Mycoplasma", "Neisseria",
				"Pasteurella", "Peptostreptococcus", "Porphyromonas gingivalis", "Pseudomonas aeruginosa",
				"penisi dickuin", "Rhizobium radiobacter", "Rickettsia", "Rochalimaea", "Rothia dentocariosa",
				"Salmonella", "Serratia marcescens", "Shigella dysenteriae", "Staphylococcus",
				"Stenotrophomonas maltophilia", "Streptococcus", "Vibrio", "Wolbachia", "Yersinia" });

		general.put("BANCO_DADOS",
				new String[] { "SQL", "banco de dado", " DML ", "database", " ORM ", "objeto relacional", "DDL",
						" procedure", " trigger", " ETL", "SGBD", "modelo em rede", "modelo hierarquico",
						"modelo fisico", "modelo conceitual", "ACID", "Atomicidade", " begin", "transaction",
						"Big data", "Business inteligence", "(BI)" });

		general.put("BRINCAR",
				new String[] { " brinca", " sorri", " esconde", " suar ", " diversao", " divertir", " passa tempo",
						" jogos ", " game ", "gamefication", " jogo ", "humor", "recreio", "recrea", " desfrutar ",
						" aproveitar ", " folgar " });

		general.put("BEBIDA",
				new String[] { "bebida", " suco", "vinho", "cerveja", "cachaça", "café", " chá ", "alcoólic",
						"refrigerante", "espumante", "champagne", "vinico", " líquido ", " bebes ", " alcool ",
						" pinga " });

		general.put("BICICLETA",
				new String[] { "catraca", "camara de ar", " celin ", " guidão ", "cabo de freio", " aro ", " pedal",
						"ciclis", "tricicl", " pneu de bicicleta ", "monocicli", "motocicli", "ciclovia",
						"ciclofaixa" });
//" bio", "fisiolog", " vivo ", " viva ", " estudo da vida ", " vida ","organismos"

		general.put("BIOLOGIA", new String[] { "Aberração cromossômica ", "Abscisão", "Ácido Nucleico ", "Actina ",
				"Aferentes ", "Agentes Etiológicos ", "raiz", "Alelo ", "Alevino ", "Amido ", "Amiloplastos ",
				"Anaeróbias ", "Anáfase ", "Androceu ", "Angiosperma ", "Antibióticos ", "Anticorpos ", "Antígeno ",
				"Autofagia ", "Autofágicos ", "Autólise ", "Avascular", "B1", "B12", "B2", "B6", "Bacilo", "Bactéria",
				"Bactériofila", "Bacteriófago", "Basidiomicetos", "Bioquímica", "Bivitelino", "Botulismo", "Briófita ",
				"Bentos", "Biocenose", "Biodegradável", "Biodiversidade", "Bioma", "Biosfera", "Biota", "Biótico",
				"Biótipo", "Cadeia alimentar", "Carcinogênicos", "Chorume", "Cálice", "Cariogamia", "Cariótipo",
				"Caroteno", "Carpelo", "Caulículo", "Célula Eucariótica", "Celulose", "Centríolos", "Centrômeros",
				"Cianobactérias", "Cianófitas", "Cianossomas", "Ciliados", "Desmossomos", "Desoxirribose", "Diatomácea",
				"Dinoflagelados", "Diploide", "Dispneia", "DNA", "Dominante", "Dormência", "Decompositores",
				"Desoxirribonucleico", "Dicotiledônea", "Divisão Celular", "Ectoplasma", "Eferente",
				"Elementos Quimiossintetizantes", "Endocitose", "Endoesqueleto", "Energia de Ativação",
				"Envoltório Celular", "Envoltório Nuclear", "Ergastoplasma", "Esclerênquima", "Escorbuto",
				"Espermatozoide", "Espícula", "Esporos", "Esporulação", "Estame", "Estigma", "Estilete", "Estômato",
				"Eucarionte", "Euglena", "Exocitose", "Exoesqueleto", "Ecologia", "Ecossistema", "Ecótipo",
				"Efeito cumulativo", "Epífitas", "Espécie pioneira", "Espermáfita", "Fator ecológico", "Fitoplâncton",
				"Fotossíntese", "Fagocitose", "Fagossomos", "Fanerógama", "Fase vegetativa", "Febre Tifoide",
				"Fibrinogênio", "Filete", "Flagelado", "Flagelo", "Floema", "Fosforilação oxidativa",
				"Fotofosforilação acíclica", "Fotólise", "Fraterno", "Fruto", "Genealogia", "Genoma", "Genes ",
				"Giminospermas", "Gineceu", "Ginecomastia", "Glicocálix", "Glicogênio", "Glicólise", "Golgi",
				"Haplodiplobionte", "Haploide", "Hemácias", "Heredograma", "Heterólogo", "Heterótrofos", "Heterozigoto",
				"Hialoplasma", "Híbrido", "Hidrato de Carbono", "Hidrólise", "Hífas", "Hipertônica", "Hipotônica",
				"Holoenzima", "Homólogo", "Homozigoto", "Hormogonia", "Hormônio ", "Habitat", "Homeostase",
				"Homeotermos", "Inclusão", "Insulina", "Interdigitações", "Interferon", "Inquilinismo", "Isogamia",
				"Klinefelter", "Lenticela", "Leucócitos  ", "Leucoplastos", "Liquens", "Lise", "Lisossomo",
				"Lixiviação", "Locus", "Metáfase", "Meiose", "Membrana Nuclear", "Membrana Plasmática ", "Meristema",
				"Mesossomas", "Metabolismo", "Metacêntrico", "Micélio", "Micoplasma", "Micrognatia", "Micrótomo",
				"Microtubulos", "Microvilosidades", "Miosina", "Mitocôndria", "Mitose", "Mixo", "Monocotiledôneas",
				"Monossacarídeos ", "Multicelular ", "Manancial", "Mutações", "Mutualismo", "Nematoide", "Nucleoide",
				"Nucléolo", "Nucleoplasmas", "Nucleotídeo", "Nicho ecológico", "Nível trófico", "Onívoro", "Organela",
				"Osmose", "Parasita", "Parede Celular", "Parede Celulósica", "Patau", "Patógeno", "Pelagra",
				"Pericarpo", "Permease", "Peroxissomas", "Pétala", "Pigmento", "Pinocitose", "Pirrófitas", "Pistilo",
				"Plâncton", "Plasma", "Plasmídeos ", "Plasmócitos", "Plasmodesmos", "Plastos", "Pluricelulares",
				"Pólen", "Polinização", "Polissacarídeos", "PPLO", "Predatismo", "Procariontes", "Prófase",
				"Progesterona", "Protease", "Protoplasma", "Protozoário", "Pseudofruto", "Pseudópodes", "Pteridófita",
				"Piracema", "Queratina", "Quimiossíntese", "Radícula Raiz", "Raiz Axial", "Raiz fasciculada",
				"Raquitismo", "Recessivo", "Retículo Endoplasmático", "Resíduos", "Retidoma", "Rhizobium", "Ribose",
				"Ribossomos", "Riquetísias", "Rizópodes", "RNA", "Saneamento básico", "Segmentação", "Sépala",
				"Síndrome de Down", "Síndrome de Edwards", "Síndrome de Klinefelter", "Síndrome de Pata",
				"uSíndrome de Turner", "Somático", "Súber", "Submetacêntrico", "Substrato ", "Seleção natural",
				"Seres consumidores", "Seres decompositores", "Seres produtores", "Silicose", "Simbiose",
				"Sucessão ecológica", "Síndrome do cri du chat ", "Talófita", "Telocêntrico", "Telófase",
				"Teratogênico", "Testosterona", "Tireoide", "Tolerância", "Transdução", "Traqueófita", "Turner",
				"Úlcera", "Unicelulares", "Univitelinos", "Ureia", "Vacúolo", "Citoplasmático", "Vascular", "Vaso",
				"Vesículas Fagocitárias", "Xantofila", "Xerofitalmia", " Xilema", "Zigoto", "Zooplâncton" });

		general.put("BIOMEDICINA", new String[] { "biomedicina", "vida", "base", "estrutura", "ordem", "ciclo",
				"mecanismo", "circulação", "absorção", "gene", "zoonomia", "zoografia", "zootecnia", "zootomia",
				"entomologia", "zootaxia", "paleontologia", "ornitologia", "entomologista", "ictiologia",
				"zoogeografia", "insetologia", "insetologista", "zooética", "pelo", "planta", "receita", "formulação",
				"infusão", "compatibilidade", "farmacologia", "antagonismo", "comprimido", "melito", "histamina",
				"xarope", "atividade", "transporte", "reservas", "calor", "receptor", "sono", "compensação", "urina",
				"digestão", "descarga", "motilidade", "lira", "cromatina", "hifa", "interfase", "sarcolema",
				"autofagia", "dendrito", "sáculo", "placa", "consolidação", "implante", "colapso", "tratamento",
				"retração", "neoplasia", "defesa imuno", "nascimento", "sangue", "aglutinação", "leucocitose",
				"leucopenia", "leucose", "eritroblastose", "hipercromia", "hemoglobinemia", "hemocomponente", "acromia",
				"anisocromia", "eritroblasto", "hemopatia", "melanemia", "soro", "epiderme", "maceração", "mononuclear",
				"sebo", "tromboplastina", "linfa", "fibroblasto", "osteodistrofia", "eritropoese", "dose", "dosagem",
				"mutação", "variante", "clone", "clonagem", "replicação", "heterose", "expressividade", "recombinação",
				"homologia", "hereditariedade", "dimorfismo", "visita", "pimenta", "imperador", "mirim", "cera",
				"aedes", "vanessa", "leiteira", "triatoma", "virologia", "esporo", "plasmódio", "deuteromiceto",
				"tanoeiro", "jacaretinga", "hamadríade", "sucuriju", "anijuaganga", "coco", "gram", "atenuação",
				"preservativo", "subcultura", "hemocultura", "pneumococo", "treponema", "píleo", "tuberculina",
				"salmonela", "mesossomo", "estafilococo", "placenta", "clivagem", "ativador", "blastocisto", "blástula",
				"trofoblasto", "somito", "ectoderma", "lanugem", "ectoderme", "alantoide", "celoma", "embriogenia",
				"epiblasto", "carbonização", "dicinodonte", "carnossauro", "trilobita", "brontossauro", "arcossauro",
				"iguanodonte", "alossauro", "fossilização", "tiranossauro", "epigrafia", "braquiossauro", "ebola",
				"arbovirose", "adenovírus", "virólogo", "polimorfismo" });

		general.put("BOLSA_VALOR",
				new String[] { " acionista", "bolsa de valor", "majoritário", "compra de ações", "venda de açoes",
						"balanço empresarial", "balanco financeiro", " pregao ", "fundo imobiliario", "valor por cota",
						"potencial de retorno", "oferta publica", " IPO ", "mercado secundario", "valorizacao",
						"bovespa", "movimentacao", "corretora de valor", "balancete" });

		general.put("BOTANICA",
				new String[] { "botânica", "herborização", "fitologia", "fitografia", "micologia", "dendrologia",
						"micetologia", "vegetal", "algologia", "fungologia", "pomologia", "rodologia", "herborista",
						"herbolário", "jardim botânico", "horto", "lineu", "ervanário", "botânico", "herborizador",
						"herbário", "pomólogo", "siccus" });

		general.put("CABECA",
				new String[] { "maxilar", "boca", "dente", "denta", "lábio", "olho", "ouvido", "orelha", "sobrancelha",
						" nuca ", "couro cabeludo", "palpebra", "narina", "labio", "cervical", "nasais", "nasal",
						"palat", " papila", " intelect", " racio", " crânio " });

		general.put("CALCADO",
				new String[] { "sapato", "sandália", "chinel", "tênis", "tamanco", "mocacim", "alpercata" });

		general.put("CALCULO",
				new String[] { "derivada", "integral", "limite ", " calculo ", " número ", " conta ", " contabilidade ",
						" estimativa ", " balanço ", " aproximação ", " orçamento ", " estima", " contagem ",
						" apuração ", "contas" });

		general.put("CAMADAS_TERRA", new String[] { "núcleo", "mágma", "crostra", "atmosfer", "ionosfera", "mesosfera",
				"manto superior", "manto inferior", "litosfera", "rarefeito", "geleira", "circulo polar", "vulcao",
				"vulcanismo", "vulcoes", "erosao", "tempestade", "massas de ar", "previsao do tempo", "correte de ar",
				"massa de ar", "correntes de ar", "nuvem", "nuvens", " maré", "superfície", "emergir", "imergir" });

		general.put("CANTO", new String[] { "voz", "cantar", "cantor", "de canto ", "canto coral", "tenor", "contralto",
				"soprano", "barítono" });

		general.put("CAPACIDADE",
				new String[] { "diferencia", "espontâne", "prodige", "gênio", "genial", "capacidade", "facilidade",
						" nato ", " inato ", "expert", "competen", "qualifica", "cursar", "perito", "pericia", "habil",
						"experiente", "experiencia", "aperfeicoa", "competente", "desempenho", " craque " });

		general.put("CARRO",
				new String[] { "volante", "cambio", "caixa de marcha", "cinto de segurança", "odômetro", "combustível",
						"escapamento", "embreagem", "capô", "porta mala", "pneu", "montadora", "veicular", "motorista",
						"ignicao", "banco do passageiro", "banco do motorista", "passageiro", " pneu ", " automóvel ",
						" automo", " veícul" });

		general.put("CASA",
				new String[] { "casa", " lar ", " morada", " morador", " morar", "residência", "palafita", "abrigo",
						" sala ", " quarto ", " cama ", " mesa ", " banheiro", " cadeira ", " garagem ", " cômodo ",
						" telhado ", " telha ", " porao ", "fogão", " toca ", " caperte ", " capacho ", " travesseiro ",
						" lencol ", " guarda roupa ", " sotao ", " tapete ", "janela", " portao ", "doméstic" });

		general.put("CELULA_ORGANICA",
				new String[] { "carioteca", "membrana", "cromossôm", "genét", " dna ", "enzima", " rna ", "proteica",
						"ribossomo", " golgi", " célula ", "carionte", " cariot", "citoplasma", "filamento", "orgânela",
						"hemac", "hemato", "fagocito", "endocito", "membrana plasmática", "sintese proteica",
						" plasto" });

		general.put("CIDADANIA",
				new String[] { "direitos", "dever", "cidadania", "civismo", "individuo", "cidadao", "obrigacao",
						"nacional", "sufragio universal", "maioridade", "servico militar", "alistamento",
						"elegibilidade", "naturalidade", "identidade", "fidalgo", });

		general.put("CIENCIA", new String[] { "científico", "método científico", "hipótese", "teoria" });

		general.put("CLASSE_SOCIAL",
				new String[] { "classe social", "rico", "pobre", "periferia", "ideologia", "rei ", "escravo" });

		general.put("CLASSE_ANIMAL", new String[] { "vertebrado", "invertebrado", "carnivo", "herbívoto", "mamífero",
				"reino", " filo ", "classe", "órdem", "família", "gênero", "espécie", "simbio" });

		general.put("CLIMA", new String[] { "árido", "tropic", "equatori", "temperad", "desert", "mediterrâneo",
				" polar ", "sertão" });

		general.put("COBRAS", new String[] { "Coral-Verdadeira", "Jararacas", "Jararacuçu", "Urutu", "Cascavel",
				"Falsa-Coral", "Jiboia", "Muçurana", "Sucuri" });

		general.put("COMBUSTIVEL", new String[] { "oxido", "eletrolise", "comburente", "combust", "renovavel", " gas",
				"oleo", "carvao", "propano", "metano", "butano", "meteno", "alcool", "diesel" });

		general.put("COMIDA", new String[] { "legume", "verdura", "fruta", "massa", "carne", "vegetariano", "culinária",
				"nutriente", "gordura", "lipid", "leite", " pão", " bolo", "pastel", "esfirra", "geleia", "manteiga",
				"queij", "nutri", "lipo", "levedura", "hortaliça", "açúcar", "proteína", "carboidrato", " mel " });

		general.put("COMIDA_REGIONAL", new String[] { " doce ", "queijo", "comida regional", "tapioca", "cuscuz",
				"prato", "culinária local", "culinária regional", "feij", "arroz", "pizza", "bolo de ", "prato de " });

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
						"desprezo", "desprezar", "cuidar", "cuidado", "esqueci", "esquecer", "positividade",
						"coerencia", "personalidade", "solidari", "razoes" });

		general.put("COMPUTADOR", new String[] { "mouse", "teclado", "monitor", "screen", " disk ", "periférico",
				"notebook", "personal computer", "main frame", "hard disk", "fonte de alimentação", "computac" });
		general.put("CONCLUSAO",
				new String[] { " feito ", " conclui", " acaba", "termina", " final", " complet", "100%", "totalidade",
						" entregue", " aceito", " interrompi", " faltan", " vencido", " praso", "conclusao" });

		general.put("CONTRADICAO", new String[] { " mas ", "porém", "contudo", "todavia", "no entanto", "entretanto",
				"contrariamento", "em desacordo", "oposicao", "opositiv", "contra" });

		general.put("CONFIRMACAO", new String[] { " Sim ", " certo", " afirma", " positiv", " negativ", " é ", "ambi",
				"confirm", "prova" });

		general.put("CONTINENTES",
				new String[] { "africa", "america", "ásia", "europa", "oceania", "artico", "antarti" });

		general.put("CONSTRUIR",
				new String[] { " sintese", " sintéti", " produzir ", " constru", " fazer ", " realiza", " faz ",
						" refaz", "etapa", "constitui ", "constituido", "constituir", "laboratóri", "desenvolv",
						"confecc" });

		general.put("COR", new String[] { "púrpura", "violet", "vermelh", "marron", "béje", "pigment", "color",
				"Amarel", "Ametista", "Anil", "Azul", "Bege", "Bordô", "Branc", "Cáqui", "Caramelo", "Carmesim",
				"Carmim", "Castanho", "Chocolate", "Ciano ", "Cinz", "Cinzento", "Cobre", "Coral", "Creme", "Dourado",
				"Escarlate", "Esmeralda", "Ferrugem", "Fúcsia", "Gelo", "Grená", "Gris", "Índigo", "Jade", "Laranj",
				"Lavanda", "Lilás ", "Loiro", "Magenta", "Malva", "Marrom", "Mostarda", "Negro", "Ouro", "Prata",
				"Pret", "Rosa", "Roxo", "Rubro", "Terracota", "Turquesa", "Verde", "Vermelho", "Violeta" });

		general.put("CORPO", new String[] { "tronco", "membro", "braço ", " mão ", " pé ", " corpo ", "abdome",
				" rins ", "renal", "exofago", "estomago", "coracao", "pulmao", "cardio", "cerebro", "cerebral",
				"pulmonar", "pulmoes", "timpano", "figado", "bexiga", "intestin", "pancrea", "coluna cervical",
				"espinha dorsal", "cerebelo", "antebraco", "umbilical", "aparelho digestivo", "aparelho reprodutor",
				"axonio", "neuronio", "corporal", "corporeo", "cortex", "podes ", "podo ", " cranio ", "lombar",
				"costela", " pulso ", "arteria", " veia ", " veias ", "vaso capilar", "vasos capilares", });

		general.put("CORPOS_CELESTES",
				new String[] { "estrela", "planeta", "asteroide", "lua ", "constela", "galáxia", "estelar", "massivo",
						"telescópio", "astrono", "órbita", "gravidade", "gravit", "eclipse", " cometa ",
						" planeta terra ", " sol ", " buraco negro ", " supernova " });

		general.put("CRUSTACEO", new String[] { "crustace", "carapaca", "placton", "bentonic", "branchiura",
				"cirripedia", "artropode", "rimipedia", "cuticula", "parafiletico", "monofiletico", " poliqueta " });

		general.put("CULTURA", new String[] { "laser", "folclo", "religi", "costum", "regional", "popular", " cultura ",
				"cultural", "culturais", "tradic", "carnaval", "reveillon", "cantiga" });

		general.put("DEFEITO_ADJETIVO", new String[] { "agressivo", "ansioso", "antipático", "antissocial", "apático",
				"apressado", "arrogante", "atrevido", "autoritário", "avarento", "birrento", "bisbilhoteiro", "bruto",
				"calculista", "casmurro", "chato", "cínico", "ciumento", "colérico", "comodista", "covarde", "crítico",
				"cruel", "debochado", "desafiador", "desbocado", "descarado", "descomedido", "desconfiado", "descortês",
				"desequilibrado", "desleal", "desleixado", "desmazelado", "desmotivado", "desobediente", "desonesto",
				"desordeiro", "despótico", "desumano", "discriminador", "dissimulado", "distraído", "egoísta",
				"estourado", "estressado", "exigente", "falso", "fingido", "fraco", "frio", "frívolo", "fútil",
				"ganancioso", "grosseiro", "grosso", "hipócrita", "ignorante", "impaciente", "impertinente",
				"impetuoso", "impiedoso", "imponderado", "impostor", "imprudente", "impulsivo", "incompetente",
				"inconstante", "inconveniente", "incorreto", "indeciso", "indecoroso", "indelicado", "indiferente",
				"infiel", "inflexível", "injusto", "inseguro", "insensato", "insincero", "instável", "insuportável",
				"interesseiro", "intolerante", "intransigente", "irracional", "irascível", "irrequieto",
				"irresponsável", "irritadiço", "malandro", "maldoso", "malicioso", "malvado", "mandão", "manhoso",
				"maquiavélico", "medroso", "mentiroso", "mesquinho", "narcisista", "negligente", "nervoso", "neurótico",
				"obcecado", "odioso", "oportunista", "orgulhoso", "pedante", "pessimista", "pé-frio", "possessivo",
				"precipitado", "preconceituoso", "preguiçoso", "prepotente", "presunçoso", "problemático", "quezilento",
				"rancoroso", "relapso", "rigoroso", "rabugento", "rude", "sarcástico", "sedentário", "teimoso",
				"tímido", "tirano", "traiçoeiro", "traidor", "trapaceiro", "tendencioso", "trocista", "vagabundo",
				"vaidoso", "vulnerável", "vigarista", "xenófobo" });

		general.put("DESEJO", new String[] { "querer", "vontade", "desej", "pretenc", "objetiv", " alvo ", "quer ",
				" meta ", "esperado", "exito" });

		general.put("DESIGNE_SYSTEM", new String[] { "token", " tag ", " css ", "design", "cores", "estilo", " cor " });

		general.put("DIMENSAO", new String[] { "altura", "largura", "espess", "profund", "peso", "tamanho", " medida",
				"metria", "metric", "escala", "distancia" });

		general.put("DINOSSAURO", new String[] { "ssauro", "jurássico", "ssaurídeo", " dinossauria " });

		general.put("DIFICULDADE", new String[] { " duro ", " dificil", " dificul", " complica", "desgastante",
				" facil", " simpl", "trabalhos", " esforç", "hercule", "possível" });

		general.put("DOCUMENTOS_PESSOAIS",
				new String[] { "carteira de trabalho", " CPF ", "carteira de identidade", "passaporte", " rg ",
						"carteira nacional de habilitação", " cnh ", "carteira de estudando", "carteira de habilitacao",
						"registro de nascimento", "reconhecimento de firma", "copia autentiticad", " CIC ", " CGC ",
						"Certidao de negativa", "certidao de nasccimento", "comprovante de alistamento", " CAM " });

		general.put("DOCUMENTAR",
				new String[] { "documento", "achado", "registro", "escrito", "fóssil", "arqueo", "arcai", "gravar",
						"gravação", "antig", "escavacao", " paleoz", "paleobot", "paleoeco", "museu", "grafia",
						"grafar", "registrar", "registrado", "registradas" });

		general.put("DOENCA",
				new String[] { "enfermidade", "vírus", "célula", "infecç", "acompanhamento", "doente", "patologia",
						"remédio", " dor ", "hospital", "internar", " leito", "cirúrgia", "doença", "clínic", "bactér",
						"fungo", "parasit", "patogên", "mutila", "ameba", "paramerci", "terapico", "terapeuta",
						"coccus ", "aids", "cancer", "tuberculo", "dor de cabeca", "febre", "vomito", "nausea",
						"falta de hapetite", "indisposiç", "cansaço", "fraquesa", "cefaleia", "contagi", "hipertensao",
						"candida", "diabet", "diarrei", "conjuntiv", "necrose", "atópic", "atopia", "secreção",
						"infectado", "estresse" });

		general.put("DOENCA_TIPOS", new String[] { "Clamídia", "Gonorreia", "HPV", "Herpes", "Tricomoníase", "Sífilis",
				" AIDS ", " Acne ", " Afta ", "Alergia", "Alzheimer", "Amebíase", "Anemia", "Anorexia", "Bipolar",
				"Bronquite", "Bronquite Aguda", "Brucelose", "Bruxismo", "Boca Seca", "Balanite", "Cálculo Renal",
				"Cancro", "Carcinoma", "Cárie", "Catarata", "Câncer", "Cirrose hepática", "Cistite", "Cólera",
				"Colesterol", "Cólica abdominal", " Coma ", "Congestão nasal", "Conjutivite", "Crupe ", "Daltonismo",
				"Depressão", "Dermatite", "Desnutrição", "Diarreia", "Difteria", "Disfagia", "Disfunção Eréctil",
				"Dor de dentes", "DST", "micose", "hiv", " asma", "BCG", "inteferon", "IFN-y", "hidrocefalia",
				"mielomeningocele", "Ejaculação precoe", "Enxaqueca", "Epilepsia", "Esclerose", "Esquizofrenia",
				"Esofagite", "Apendicite", "Fadiga crônica", "Faringite", "Fibroma", "Flatulência", "Foliculite",
				"Frieira", "Fotossensibilidade", "Galactosemia", "Gastrite", "Gengivite", "Gigantismo", "Glaucoma",
				"Glicemia", "Gota", "Gravidez Ecóptica", "Halitose", "Hemanginoma", "Hematomacrose", "Hemofilia",
				"Hemorróida", "Hepatite", "Hiperglicemia", "Hipertensão", "Hipertricose", "Hipertrofia", "Hipoglicemia",
				"Infecção urinária", "Infertilidade", "Insolação", "Insónia", "Insuficiência cardíaca",
				"Intolerância à lactose", "Intoxicação", "Lábio leporino", "Lalofobia", "Laringite", "Lúpus", "Malária",
				"Melanoma", "Miocardi", "Miopia", "Morte Súbita Cardíaca", "Nanismo", "Náusea", "Neuropatia",
				"Obesidade", "Obstipação", "Osteoartrose", "Osteoporose", "Osteopetrose", "Papeira", "Paranóia",
				"Parkinson", "Piromania", "Pneumonia", "Queimadura Solar", "Queratose", "Resfriado", "Rinite",
				"Rubéola", "Sarampo", "Tendinite", "Tétano", "Tetraplegia", "Tiroidite", "Tosse", "Trigliceridemida",
				"Tuberculose", "Urticária", "Varicela", "Varíola", "Variz", "Verruga", "Vigorexia", "VIH", "Zumbido",
				"gripe" });

		general.put("DOENCA_TERAPIA_TRATAMENTO",
				new String[] { "antidepressiv", "antitermico", "antibiotico", "corticoide", "analgésico",
						"quimioterapi", "radioterapi", "anti", "fisioterapia", "terapia", "acupuntura" });

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
						" viga ", " pilar ", "heliponto", "mesanino", "escadaria", "corta fogo", "fachada", "luminária",
						"pedreiro", " reboco ", "azulejo", " ceramic", " ceramist", "tomada eletrica", "camara" });

		general.put("EMPRESA", new String[] { "sócio", " dono ", "fundador", "capital", "lucro", "prejuízo", "balanço",
				"corporacao", "corporati", "fluxo de caixa", "hierarqui", "companhia ", "empresa", "cartel" });

		general.put("EMPREGO", new String[] { "emprego", "trabalhador", "funcionári", "jornada", "labuta",
				"trabalho formal", "trabalho informal", "patrao", "empregado", "trabalhis", "bater ponto",
				"trabalhado autonomo", "pessoa juridica", "CLT", "profissional", "capacitacao profissional",
				"contrato de trabalhao", "ocupacional", "demissao", "demiti", "carreira", "profissao", "admissao",
				"carteira assinada", "ferias", "decimo terceiro", "salario minimo", "previdencia soacial", "aposenta",
				"tempo de serviço", "prestacao de serviço", "FGTS", "INSS", "INPS", "seguridade", "aposentado" });

		general.put("ENERGIA",
				new String[] { "energia cinetica", "energia potencial", "elastica", "gravitacional", "nergia termica",
						"energia quimica", "energia eólica", "energia nuclear", "fusão", "fissão", " gerador ",
						"nuclear", "radioativa", "radiação", "bateria", "acumulador" });

		general.put("ENSINO",
				new String[] { " ler ", "escrever", "aprender", "professor", "escola", "faculdade", "universidade",
						"aluno", "aula", "docente", "dicente", "pedago", "cogni", "profiss", "formacao", "diploma",
						"mentor", "leciona", "licenciatura", "cátedra", "didatic", "celegio", "alfabetiza", "academi",
						"instrução", "informação", "compreensão", "entendimento", "conhecimento", "educacionais",
						"aprendizagem", "leitura", "ditado", "agogo" });

		general.put("ELETRICIDADE",
				new String[] { "enérgia", "elétric", " volt ", " ampere ", " choque ", "bobina", " imã ", " indutor ",
						"indução", " capacit", " resist", "circuito", "magnét", " condut", "sensor",
						"corrente contínua", "gauss", "carga", "dínamo" });

		general.put("ELETRICIDADE_ENGENHARIA",
				new String[] { "transformador", "campo elétrico", "campo magnético", "tensão eletrétric", "voltagem",
						"amperagem", "load baster", "subestação", " TC", " TP", "de corrente", "de potencia", "dieletr",
						"inversor", "fasor", "indutor", "isola", "de manobra", "fase", "abertura de chave",
						"fechamento de chave", "talabarte", "para-raio", "aterramento", "medidor de energia",
						"eletrotecnic", "eletric", "tarifa", "cabeamento", " fio ", " fiacao ", " fios ", "isolante",
						"disjuntor", "fusível" });

		general.put("ELETRONICA",
				new String[] { "semicondutor", "eletronic", "componente", "diodo", "trasistor", "chip",
						"circuito integrado", "processador", "placa ", "display", "piezo", "cristal", "fásico",
						" diodo ", "tiristor", "digital" });

		general.put("EQUIPAMENTO_SOM", new String[] { "microfone", "pedestal", "placa de som", "audio", "captador",
				"fone de ouvido", "auto-falante", "auto falante", " sonda ", " sonar ", " eco" });

		general.put("ERAS_GEOLOGICAS",
				new String[] { "arqueozoi", "proterozoi", "paleozoi", "mezozoi", "cenozoi", "pangea",
						"formacao dos continentes", "era do gelo", "geologica", "cretáceo", "neolit", "paleolit",
						"mesozoic", "era glacial" });

		general.put("ERGONOMIA", new String[] { "lesão", "esforço", "repetitivo", "postura", "ergonômi", "fisioter",
				"massagem", "RPG" });

		general.put("ESCRITOS", new String[] { "livro", "artigo", "revista", "folheto", "corpus", "biblioteca",
				"bibliotecário", "livraria", "filoteca" });

		general.put("ESPORTE_RADICAL",
				new String[] { " salto", " Saltar", " cair", " queda", "pular", "jogar-se", "impacto", "aceleracao",
						"tunel de vento", "oculos de protecao", "grade altura", "instrutor de salto", "asa delta",
						"body jump", "trilha ", "acampa", "escotismo", "escoteiro", "canoagem", "surf", "triatle",
						"bibicross", "Skate", "skatis", "Rafting", "voo livre", "Rapel", "paraquedis", "Mountain bike",
						"Arborismo", "parkour" });

		general.put("ESTACOES_ANO", new String[] { "primavera", "verão", "outono", "inverno", "ventania", "moncoes",
				"estacao", "hiberna" });

		general.put("ESTADOS_BRASIL",
				new String[] { "rio grande do sul", "santa catarina", "paraná", "são paulo", "rio de janeiro",
						"minas gerais", "curitiba", "bahia", "alagoas", "pernambuco", "paraíba", "sergipe", "ceará",
						"rio grande do norte", "tocantins", "mato grosso", "brasília", "maranhão", "amapá", "roraima",
						"amazonas", "acre", "goiás" });

		general.put("ESTATISTICA",
				new String[] { "estatística", "gráfico", "descritiva", "dados", "consolida", "inferência", "demografi",
						"modelo", "critério", "amostra", "desvio padrao", " media", " mediana ", "erro ",
						"intervalo de confianca", "dispersao", "normalizacao", "probabili", "estimar", "estimativa",
						"modelar", "prever", "previsao", "sumarizacao", "curva normal", " PCA ", "Bayes", " vies ",
						" envies", "estratato", "estratifica" });

		general.put("ESCOLA",
				new String[] { "sala de aula", "pátio", "merenda", "intervalo", "diretor", "secretaria", "currículo",
						"matérias", "disciplina", "assunto", "formação", "notas", "quadro negro", "lousa",
						"cadeira escolar" });
		general.put("ESPORTE",
				new String[] { "exercício", "fisico", "atleta", "esporte", "jogo", "partida", "competição", });

		general.put("ENGENHARIA_PRODUCAO",
				new String[] { "acessoria do produto", "acesso dedicado", "acidente de consumo", "corretiv", "preventi",
						"manuten", "acondicionamento", "controle de estoque", "custo efetivo", "logistic",
						"segurança do traba", "cesmit", " EPI ", "engenharia de produção", "cadeia produt",
						"planta da fábrica", "linha de pro", "curva de banheira", " gestão ", " armaze" });

		general.put("EXPLICACAO", new String[] { " pois ", "porque", "concluo", "significa", " logo ", "assim",
				"dito isto", "então", "dessa forma", "por isso", "podemos perceber que", " então ", "fica claro" });

		general.put("EXCLUSAO",
				new String[] { "remove", "deleta", "jogar fora", "exclui", "restringe", "anula", "remocao", "restri" });

		general.put("FAMILIA",
				new String[] { "casal", "casamento", "familia", "matrimônio", "separacao", "pensão", "filhos",
						"divórcio", "filho", "filha", "pai", "mãe", " neto ", " neta ", " avo ", " avós ",
						"descendente", "descendencia", "linhagem", "herdeiro", "materna", "paterna", "adotado" });

		general.put("FENOMENO_NATUREZA", new String[] { "chuva", "chuvo", "chuvi", "chove", "raio", "vento", "clima",
				"meteorolog", "desastre", "cataclisma", "tsunami", "tremor de terra", "cismico", "terremoto" });

		general.put("FESTA",
				new String[] { "festa", "orquestra", "dança", "balada", "fanfarra", "trio elétrico", "carro de som",
						"radiola", " DJ'", "mesa de som", " MC'", " bolo ", "comemora", "aniversário", "parabéns",
						"confrater" });

		general.put("FISICA", new String[] { "dimensão fisica", " astronom", "espaço tempo", "estado da matéria",
				" luz ", "ótica", "óptic", " termico", "termodina", "mecânic", " solidos ", " gasoso", " inercia",
				"Celsius", "Fahrenheit", "Kelvin", "(SI)", "sistema internacional de unidades", "termométrica",
				"termodinamica", "de Condução", "por Condução", "Convecção", "Irradiação", "Calor sensível",
				"Calor latente", "dilatação", "estado de agregação", "estado físico", "coeficiente de dilatação",
				"Dilatação linear", "Dilatação superficial", "Dilatação volumétrica", "entropia", "Calor específico",
				" calor", " frio", "fogo", " plasma ", "dimensão", "temperatura", "movimento", "velocidade" });

		general.put("FISICA_FORCA",
				new String[] { "força eletrica", "forca aplicada", "forca sobre", "forca exercida", " aceleracao ",
						" potencia ", "movimento retilineo", "movimento uniforme", "velocidade media",
						"velocidade instantanea", " trajetoria", "newton", "fisica classica", "alavanca", " roldana",
						"algebra fasorial", "coeficiente de atrito", "forca de atrito", "quantidade de movimento",
						"força", "potencial" });

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
						"soldado", "recruta", "coronel", "capitão", "comandante", "almirante", "major", "delegado",
						"polícia" });

		general.put("FORMAS",
				new String[] { " linha ", " círcul", " triângular", " quadrado", "pentagono", "hexagono", " cubo ",
						" cilindr", " esferico", "conico", " toróide", "agono", "angul", " aresta", "edro ", "trapesio",
						"paralelogramo", "paralelepidepdo", "losango", " bola", "globo", "globular", "cubico", "cubica",
						"lineo", "linea", "superfici", "morfo" });

		general.put("FRUTA", new String[] { "Abacate", "Abacaxi", " Abiu ", "Abricó", "Abrunho", " Açaí ", "Acerola",
				"Alfarroba", "Ameixa", "Amêndoa", "Amora", "Ananás", "Anona", "Araçá", "Arando", "Araticum", " Ata ",
				"Atemoia", "Avelã", "Babaco", "Babaçu", "Bacaba", "Bacuri", "Bacupari", "Banana", " Baru ", "Bergamota",
				"Biribá", "Buriti", "Butiá", "Cabeludinha", "Cacau", "Cagaita", "Caimito", " Cajá ", " Caju ",
				"Calabaça", "Calabura", "Calamondin", "Cambucá", "Cambuci", "Camu-camu", "Caqui", "Carambola",
				"Carnaúba", "Castanha", "Castanha-do-pará", "Cereja", "Ciriguela", "Ciruela", " Coco ", "Cranberry",
				"Cupuaçu", "Damasco", "Dekopon", "Dendê", "Dióspiro", "Dovyalis", "Durião", "Embaúba", "Embaubarana",
				"Engkala", "Escropari", "Esfregadinha", " Figo ", "Framboesa", "Fruta-do-conde", "Fruta-pão", "Feijoa",
				"Figo-da-índia", "Fruta-de-cedro", "Fruta-de-lobo", "Fruta-do-milagre", "Fruta-de-tatu", "Gabiroba",
				"Glicosmis", "Goiaba", "Granadilla", "Gravatá", "Graviola", "Groselha", "Grumixama", "Guabiju",
				"Guabiroba", "Guaraná", "Hawthorn", "Heisteria", "Hilocéreo", "Ibacurupari", "Ilama", "Imbe", "Imbu",
				"Inajá", "Ingá", "Inharé", "Jabuticaba", "Jaca", "Jambo", "Jambolão", "Jamelão", "Jaracatiá", "Jatobá",
				"Jenipapo", "Jerivá", "Juá", "Jujuba", "Kiwi", "Kumquat", "Kinkan", "Kino", "Kiwano", "Kabosu",
				"Karité", "Laranja", "Limão", "Lima", "Lichia", "Longan", "Lucuma", "Lacucha", "Lulo", "Lobeira",
				"Langsat", "Laranja-de-pacu", "Mabolo", "Maçã", "Macadâmia", "Macaúba", "Mamão", "Mamey", "Mamoncillo",
				"Maná-cubiu", "Manga", "Mangaba", "Mangostão", "Maracujá", "Marang", "Marmelo", "Marolo", "Marula",
				"Massala", "Melancia", "Melão", "Meloa", "Mexerica", "Mirtilo", "Morango", "Murici", "Naranjilla",
				"Nectarina", "Nêspera", "Noni", " Noz ", "Noz-pecã", "Noz-macadâmia", "Oiti", "Oxicoco", "Orangelo",
				" Pera ", "Pêssego", "Pitanga", "Pinha", "Pitaia", "Pitomba", "Pitangatuba", "Pindaíba", "Pequi",
				"Pequiá", "Physalis", "Pulasan", "Pomelo", "Pupunha", "Puçá", "Patauá", "Pajurá", "Pixirica",
				"Pistache", "Quina", "Quiuí", " Romã ", "Rambai", "Rambutão", "Rukam", "Saguaraji", "Salak", "Santol",
				"Sapota", "Sapoti", "Sapucaia", "Saputá", "Seriguela", "Sorvinha", "Tangerina", "Tamarindo", "Tâmara",
				"Toranja", "Tucumã", "Taiuva", "Tapiá", "Tarumã", "Tangor", "Tucujá", " Uva ", "Umbu", "Uvaia",
				"Uchuva", " Umê ", " Uxi ", "Vacínio", "Veludo", "Vergamota", "Wampi", "Xixá", "Yamamomo", "Yuzu",
				"Zimbro", });

		general.put("GENETICA",
				new String[] { "genétic", "gameta", " mutacao", " evolut", " epidemiologia", "taxonomi", "eugenia",
						"ribonucle", "codigo genetico", " gene", " timina", " guanina", " citocina", "uracila",
						" amina", "congênit", "biologia", "genes", "material", "hereditário", "cromossomos" });

		general.put("GENITALIA_E_EROGENAS",
				new String[] { "pênis", "vagina", "ânus", "púbis", "glúteo", " anal ", " útero ", "testículo", "genita",
						"petasma", " telico ", " eroti", "mamilo", "ovário", "grandes labios", "pequenos labios",
						"clitoris", " glande ", "prepucio", "zona carnosa", "erecao", "lubrificacao", "orgasmo",
						"erogen", "fluido vaginal", " semen ", "seminal", "prostat", "pelvi", "trompas de falopio",
						"nadegas", "monte de venus", "uretra", "perianal" });

		general.put("GESTAO",
				new String[] { "organizacoes", " gestão", "gerir", "gestor", " gerencia", " administrar",
						"processo decis", "demanda", "gerente", "coordena", "chefe", "chefia", "lider", "coach",
						"recursos humanos", " RH ", "colaborador", "funcionario", "coordenador", });

		general.put("GEOMETRIA",
				new String[] { "pitagor", "distancia euclidi", "o ponto", "determinado ponto", " reta ", " plano carte",
						"dimensiona", "a dimensao", " retangu", " triang", " cartesi", " diágon", " raio ", " circul",
						" esfer", " tangen", " perpendicular", "posicao relativa", "comprimento", "area", "volume",
						"geometri", "calculo integral", "linha curva", "linha reta", "segmento de reta", "paralelismo",
						" angulo", " angular", " vetor", " escalar", "central", "concentri" });

		general.put("HIGIENE", new String[] { "banho", "escova", "pentea", "lavar", " aceio", " higia", " higien",
				"limpeza", "limpar", "sabão", "sabonete", "limpo", "lavagem", "enxagu", "detergente", "limpador" });

		general.put("HISTORIA", new String[] { " data ", "acontecimento", " evento ", " fato ", " momento ", "civiliza",
				"povos", "histori", "colonial", "coloniza" });

		general.put("HORTALICAS",
				new String[] { "Abóbora", "Abobrinha", "Acelga", "Aipo (ou salsão)", "Alcachofra", "Alface", "Alfafa",
						"Almeirão", "Aspargo", "Berinjela", "Bertalha", "Brócolos", "Cebola", " Alho ", "Alho poró",
						"Cebola-roxa", "Chicória", "Chuchu", "Cogumelos", "Shiitake", " Couve ", "Couve-de-bruxelas",
						"Couve-flor", "Couve-galega", "Endívia", "Funcho", "Escorcioneira", "Espinafre",
						"Feijão e ervilha", "Azuki", "Brotos de feijão", " Fava ", "Guandu", "Lentilha", "Feijão-preto",
						" Soja ", "Vagem", "Jiló", "Maxixe", "Milho", "Pepino", "Pimentão", "Pimenta-verde",
						"Pimenta-vermelha", "Jalapeño", "Malagueta", "Páprica", "Quiabo", "Ora-pro-nóbis", "Batata",
						"Batata-doce", "Beterraba", "Cenoura", "Gengibre", "Inhame", "Jícama", "Mandioca", "aipim",
						" Nabo ", "Rabanete", "Rábano", "Repolho", "Rúcula", "Rutabaga", "Taioba", "Tomate",
						"Mandioquinha", "batata-baroa", " Taro ", " Vará " });

		general.put("IGREJA",
				new String[] { " padre", "pastor", "sacerdote", "igreja", "diacono", "presbitero", " papa ", "monje",
						" crente", "evangelho", " clero ", "inquisicao", "crucificacao", "cruzadas",
						"companhia de jesus", "jesuita", "jesus", "catequiza", "comunhao", "batismo", "baptismo",
						" batiza", " culto ", " adora", " biblia", "alcorao", " torá ", "congregac", " seita",
						"doutrina", "dizimo", "sacrificio", "puntific", "novo testamento", "antigo testamento",
						"religiosi", "membro da igreja", " cruz" });

		general.put("INDUSTRIA",
				new String[] { "fábrica", "insumo", "produção", "manufatura", "industria", "setor primário",
						"bens de consumo", "materia-prima", "bens duráveis", "bens semi-duráveis", "bens não-duráveis",
						"taylor", "ford", "toyot" });

		general.put("INFECTOLOGIA",
				new String[] { "zoster", "zoose", "zona", "zomoterapia", "xifopagia", "xerostomia", "xantocromia",
						"vultuosidade", "vulnerário", "volvo", "vitropressão", "vitiligo", "hirsutismo",
						"hipotireoidiano", "hipogonadismo", "hipogenitalismo", "hemocromatose", "adenenfraxia",
						"adenalgia", "acromicria", "melanorreia", "gastrose", "gastroscopia", "gastresofagite",
						"enteropatia", "enterocolite", "duodenite", "aquilia", "acloridria", "miocardiopatia" });

		general.put("INFORMATICA", new String[] { "designe", "algorítmo", "computação", "informatica",
				"sistema computa", "processamento" });

		general.put("INFORMATICA_SUPORTE", new String[] { "wireless", "roteador", " LAN ", " WAN ", "hardware",
				"internet", "computador", "drive", "blowfish", "online", "web" });

		general.put("INTELIGENCIA_ARTIFICIAL",
				new String[] { "rede neural", "deep learning", "aprendizado de máquina", "data mining", "analytics",
						"machine learning", "redes neurais", "multicamada", "aprendizado profundo", "classificação",
						"predicao", "clusterizacao", "supervisionado", "por reforco", "reinforcement", "classificador",
						" label ", "markov", "confusion matrix", "matriz de confusao", "acurácia", "recall",
						"agente inteligente", "multiagente", "agentes inteligentes" });

		general.put("INSTRUMENTO_MEDICAO", new String[] { "calibra", "exatidao", "precisao", "resolucao",
				"equipamento padrao", "erro de paralaxe", "medidor digital", "medidor eletromecanico", "analogico" });

		general.put("INSTRUMENTO_MUSICAL",
				new String[] { "corda", "sopro", "percurssão", "melodia", "armonia", " kar ", " midi ", "acústico",
						" afina", " tonal", " ton ", "luthier", "instrumento music", "castanhola", "chocalho", "gongo",
						"pandeireta", "pandeiro", "pratos", "reco-reco", "sino", "tambor", "tamborim", "triângulo",
						"vibrafone", "xilofone", "clarinete", "flauta", "flautim", "gaita", "saxofone,", "corneta",
						"trombone", "trompa", "trompete", "tuba", "bandolim", "berimbau", "cavaquinho", "contrabaixo",
						"guitarra", "harpa", "ukulele", "viola", "violão", "violino", "violoncelo", "acordeão", "piano",
						"sintetizador" });

		general.put("INSETO", new String[] { "abelha", "formiga", "borboleta", "inseto", "mosca", "mosquito",
				"pernilongo", "inseticida", " escorpi", "aracn", "besouro" });

		general.put("IDIOMA", new String[] { "dialet", "tradução", "intérprete", "falar", "idioma", "intercambio",
				"dicionário", "gramática", "sintáxe", "semântica", "palavra", "fonema", "vernácul", "estrangeiris",
				"Africâner", "Albanês", "Alemão", "Aurebesh", "Árabe", "Armênio", "Azerbaijano", "Basco", "Bengali",
				"Bielo-russo", "Birmanês", "Bósnio", "Búlgaro", "Catalão", "Cazaque", "Cebuano", "Chichewa", "Chinês",
				"Cingalês", "Coreano", "Crioulo haitiano", "Croata", "Dinamarquês", "Eslovaco", "Esloveno", "Espanhol",
				"Esperanto", "Estoniano", "Finlandês", "Francês", "Galego", "Galês", "Georgiano", "Grego", "Hebraico",
				"Hindi", "Hmong", "Holandês", "Húngaro", "Igbo", "Indonésio", "Inglês", "Ioruba", "Irlandês",
				"Islandês", "Italiano", "Japonês", "Javanês", "Kannada", "Khmer", "Laosiano", "Latim", "Letão",
				"Lituano", "Macedônico", "Malaiala", "Malaio", "Malgaxe", "Maltês", "Maori", "Marathi", "Mongol",
				"Nepalês", "Norueguês", "Persa", "Polonês", "Português", "Romeno", "Russo", "Sérvio", "Sesotho",
				"Sudanês", "Sueco", "Tadjique", "Tagalo", "Tailandês", "Tâmil", "Tcheco", "Telugo", "Turco",
				"Ucraniano", "Urdu", "Usbeque", "Vietnamita", "Yddish", "Zulu" });

		general.put("JORNALISMO",
				new String[] { "reporter", "reportagem", "noticia", "divulgac", "matéria jorn", "comentarista",
						" news ", " cronica ", "resenha", "redator", "cronista", "imprensa", "comunicacao de massa",
						"fonte de informação", "credibilidade" });

		general.put("LEIS",
				new String[] { "legisla", "forum", "justiça", "crime", "juiz", "fiscal", "fisco", "constituição",
						"constitucio", "tribut", "petição", "advoga", "forense", " OAB ", "jurispruden", "comarca",
						"acusacao", " reu ", " dever ", " deveres ", " direito ", " direitos ", "advoca", " promotor",
						"processo penal", "processo civil", " ilegal ", " legal ", "burocra", "juricia",
						"suprema corte", "corte marcial", "juridic", "leis", "judiciario " });

		general.put("LEITURA", new String[] { "texto", " textua", "poesia", "poema", "poeta", "poetiza", "escrita",
				"obra", "literário", " leitor " });

		general.put("LIGACAO_QUIMICA",
				new String[] { "tabela periódica", "ligação metalica", " iônic", " octeto", " atomic",
						"elemento químic", "valencia ", "gás nobre", "ligacoes quimicas", " metal", " ametal",
						" cation", " anion", " catodo", " anodo" });

		general.put("LINGUAGEM_PROGRAMACAO",
				new String[] { "linguagem de ", "compila", "código de maquina", "binário", "algoritmo", " lógica de",
						"codigo binario", " compilado", "escrita de codigo", "codificacao", " UML ", "JSON", "XML",
						"html", " SOA ", " GIT ", "versionamento", " Agile ", "WaterFall", "application", " TDD ",
						" BDD ", "teste unitario", "unit test", "cluster", "normaliza", "teste integra", "e2e",
						"multiplataforma", "crossplataforma", "linguagem funcional", "orientacao a aspecto",
						"procedural", "REST ", "W3C", " boolean", " boleano ", "framework", " lib ", " java", " API ",
						" sistemas de informacao ", " Ciencia de dados ", "códigos" });

		general.put("LIQUIDO",
				new String[] { "aqua", "água", "aquos", "húmid", "soluve", "solven", "solver ", "dilui", " gota ",
						"gotícula", " dissolv", "enchente", "alagamento", "alagar", "alagado", " aquifer",
						"lençol freatico", "fonte termal", "cacimba", "poço artesiano", "absolv", "absorv", "hidrat",
						"desidrat" });

		general.put("LITERATURA",
				new String[] { "renascimento", "renascen", "barroc", "romanti", "modernis", "impression", "abstrat",
						"realis", "naturalis", "ufanis", "trovador", "quinhetis", "seissentis", "moderno" });

		general.put("LOCAL",
				new String[] { " rua ", "bairro", " cidade ", "município", "distrito", " ilha ", " estado ", "país",
						" região ", "continente", "continental", " local ", "municipa", "estadua", " federa", "aldeia",
						"arquipelago", "povoado", " lugar ", " espaco ", " zona ", "regiao", "regioes", "local",
						"locais", "naciona" });

		general.put("LUTA", new String[] { " luta", "conflito", "arma ", " tiro ", "bomba", "confronto", "enfrenta" });

		general.put("MACRO_ATIVIDADES_ECONOMICAS",
				new String[] { "insdustria", "agricultura", "pecuária", "primário", "secundário", "terceário",
						"extrativismo", "pesca", "psicultura", "cultivo", "icultura", "sazonal", "turismo" });

		general.put("MAQUINA", new String[] { "máquina", "auto", "robô", "navio", "motor", "dispositivo", "ferramenta",
				"sistema", "equipamento " });

		general.put("MATEMATICA",
				new String[] { "equação", "fórmula", "enunciado", "multiplicação", "adição", "subtracação", "divisão",
						"geometria", "calcul", "algebr", "axioma", "euclid", "numero", "kepler", "fracoes", "matematic",
						" área ", "equacao", "raíz quadrada", "potenciação", "derivac", "logarit", "polinomi", "matriz",
						"determinante", " grau ", "infinito", " tende a", "fatorial" });

		general.put("MATERIAL_ESCOLAR",
				new String[] { "caneta", "lápis", "caderno", "borracha", "mochila", " farda ", "regua", "apontador",
						"papel", " cola ", " tesoura ", " estojo ", " corretivo ", " lápis de cor ", " esquadro ",
						" transferidor ", " compasso " });

		general.put("MATERIAIS",
				new String[] { " materiais ", " material ", "plastico", " concreto ", " corporais ", " físicos ",
						"mundanos", "elementos", " objetos ", " peças ", " aparatos ", " utensílios ", " equipagem ",
						"gesso", "corrente", "polia", "parafuso", "prego", "martelo", "marreta", "picareta", "alicate",
						"serrote", "serra", "chave de fenda", "chave de boca" });

		general.put("MATRIZ_ENERGETICA", new String[] { "matriz energética", "cota de carbono", "hidro elétrica",
				"termelétrica", "térmic", "fonte energética", "solar", "eólic", "biomassa" });

		general.put("MEDICAMENTOS", new String[] { "Acetazolamida", "Acetilcisteína", "Acetilcolina", "Aciclovir",
				"Adenosina", "Adrenalina ", "Adsorvente", "Albendazol", "Albumina ", "Alendronato ", "Alfentanilo ",
				"Alfuzosina", "Alopurinol", "Alprazolam", "Alprostadil", "Amicacina", "Amidotrizoato ", "Aminoácidos",
				"Aminofilina", "Aminohidroximetilpropanolol ", "Amiodarona", "Amissulprida", "Amitriptilina",
				"Amlodipina", "Amoxicilina", "Amoxicilina ", "Ampicilina", "Anfotericina ", "Artemeter ", "Artesunato ",
				"Asparaginase ", "Aspartame", "Atazanavir", "Atenolol", "Atorvastatina", "Atracúrio", "Atropina",
				"Aurotiomalato ", "Azatioprina ", "Azelastina", "Azelastina, ", "Azitromicina", "Azoto ", "Baclofeno ",
				"Beclometasona", "Bendazac ", "Benzalcónio ", "Benzidamina ", "Benzilpenicilina ", "Benzoato ",
				"Beraxoteno", "Betahistina", "Betametasona", "Betametasona, ", "Betametazona ", "Betaxolol",
				"Betaxolol ", "Bevacizumab", "Bezafibrato", "Bicalutamida", "Bicarbonato ", "Biperideno", "Bisoprolol",
				"Bleomicina ", "Bromazepam", "Bromexina", "Bromocriptina", "Budenosido", "Budesonida", "Bupivacaina",
				"Bupropiona", "Buspirona", "Bussulfano", "Butilescopolamina", "Cabergolina", "Calamina", "Cálcio",
				"Calcipotriol", "Calcipotriol ", "Calcitonin", "Calcitriol", "Calcitriol(vit ", "Cápsulas: ",
				"Captopril", "Carbamazepina", "Carbamida", "Carboplatina ", "Carvedilol", "Cefazolina ", "Cefotaxima",
				"Ceftazidima ", "Ceftriaxone", "Cefuroxima", "Cetirizina", "Cetrimida", "Cetrimida+Ácido ",
				"Cianocobalamina ", "Ciclofosfamida", "Ciclopentolato", "Cicloserina", "Ciclosporina ", "Cimetidina",
				"Cinarizina", "Ciprofloxacina", "Ciproterona", "Ciproterona ", "Cisplatina ", "Citalopram",
				"Citarabina ", "Citocromo ", "Claritromicina", "Clindamicina", "Clobetazol", "Clodrónico, ",
				"Clofazimina", "Rifampicina", "Clomifeno", "Clomipramina", "Clonazepam", "Clonidina", "Clonidine",
				"Clopidogrel ", "Clorambucil ", "Cloranfenicol", "Clorfeniramine", "Clorodiazepóxido", "Clorohexidina",
				"Clorometina ", "Cloropromazina", "Cloroquina", "Clotrimazol", "Cloxacilina", "Cloxazolam", "Codeína",
				"Colchicina", "Cotrimoxazol", "Cromoglicato", "Dacarbazina ", "Dactinomicina ", "Dantroleno", "Dapsona",
				"Darbepoetina ", "Daunorrubicina ", "Deferroxamina", "Denominações ", "Desloratidina", "Desmopresina ",
				"Desogestrel", "Desogestrel ", "Dexametasona", "Dexametasona ", "Dextrano ", "Diazepam", "Diclofenac",
				"Didanosida ", "Digoxina", "Diltiazem", "Dimenhidrinato", "Dimercaprol", "Dimeticone", "Dimetindeno, ",
				"Dinitrato ", "Dinoprostona ", "Diosmina ", "Dipiridamol", "Dipivefrina", "Diprofilina", "Dissulfiram",
				"Dobesilato ", "Dobutamina ", "Docetaxel", "Domperidona", "Donepezil", "Dopamina ", "Dorsolamida",
				"Dorsolamida ", "Doxiciclina", "Doxilamina ", "Doxorrubicina ", "Droperidol", "Drospirenona ",
				"Econazol", "Efavirenze ", "Efedrina ", "Eletrólitos ", "Emtricitabina ", "Enalapril",
				"Ergocalciferol ", "Ergotamina ", "Eritromicina", "Eritropoietina ", "Escitalopram", "Espaglúmico, ",
				"Espectinomicina", "Espiramicina", "Espironolactona", "Estavudina ", "Estradiol", "Estradiol ",
				"Estreptomicina", "Estreptoquinase ", "Estriol", "Estrogénios ", "Etacridine ", "Etambutol",
				"Etambutol ", "Etinilestradiol ", "Etionamida", "Etofenamato", "Etomidato", "Etoposido ", "Fenilefrina",
				"Fenintoína: ", "Fenobarbital", "Fentanilo", "Filgrastim ", "Finasterida", "Fitomenadiona: ",
				"Flavoxato", "Flucloxacilina", "Fluconazol", "Flufenazina", "Flumazenil", "Flunitrazepam",
				"Fluocinolona ", "Fluoresceína", "Fluoreto ", "Fluorometolona", "Fluorouracilo ", "Fluoxetina",
				"Flupentixol ", "Flurazepam", "Flutamida", "Fluticasona ", "Folinato ", "Formoterol ", "Furosemida",
				"Fusafungina", "Ganciclovir ", "Gase ", "Gel ", "Gelatina ", "Gencitabina", "Gentamicina ",
				"Gestodeno ", "Glibenclamida", "Glicerina", "Gliclazida ", "Glucagom", "Glucosamina", "Glutaraldehido",
				"Glycina", "Gonadotrofina ", "Goserelina", "Gramicidina ", "Griseofulvina", "Haloperidol", "Halotano",
				"Heparina", "Heparinoide", "Hexetidina", "Hialuronato ", "Hialuronidase", "Hidralazina",
				"Hidroclorotiazida", "Hidrocortisona", "Hidroxicarbamida( ", "Hidroxicobalamina", "Hidróxido ",
				"Hidroxipropilmetilcelulose", "Hidroxizina", "Hipoclorito ", "Hipossulfito ", "Homatropina",
				"Ibuprofeno", "Ifosfamida", "Imatinibe", "Imipenem ", "Imipramina", "Imunoglobulina ", "Indapamida",
				"Indinavir ", "Indometacina", "Interferão ", "Iodo-Iodetado", "Iodopovidona", "Iopamidol", "Iopidol ",
				"Iopodato", "Iopromida", "Ioversol", "Ioxitalamato", "Ipratrópio", "Irbesartan ", "Isofluorano",
				"Isoniazida", "Isoniazida ", "Isoprenalina ", "Isotretinoína ", "Isotretinoina ", "Itraconazol",
				"Kanamicina ", "Ketamina", "Ketoconazol", "Ketotifeno", "Labetalol", "Lactato ", "Lactulose",
				"Lamivudina ", "Lamotrigina", "Latanoproste", "Latanoproste ", "Letrozol", "Levamisol", "Levedopa ",
				"Levofloxacina", "Levomepromazina", "Levonorgestrel", "Levotiroxina: ", "Lidocaína", "Lidocaína ",
				"Lípidos:", "Lisinopril", "Lítio", "Loperamida", "Lopinavir ", "Loratadina", "Lorazepam", "Manitol",
				"Mebendazol", "Mebeverina", "Medroxiprogesterona", "Mefloquina", "Megestrol", "Melfalano",
				"Mercaptopurina", "Mesna", "Messalazina", "Metadona) ", "Metamizol", "Metformina", "Metilcelulose",
				"Metildopa", "Metilergometrina ", "Metilfenidato ", "Metilprednisolona", "Metimazol ", "Metipranolol ",
				"Metoclopramida", "Metoprolol", "Metotrexato", "Metoxsaleno ", "Metronidazol", "Metronidazol:",
				"Micofenato ", "Miconazol", "Midazolam", "Misoprostol ", "Mitomicina", "Mitoxantrona", "Molgramostim ",
				"Mometasona", "Mononitrato ", "Montelucaste", "Morfina", "Nadroparina ", "Nalidíxico, ", "Naloxona",
				"Naltrexona", "Nandrolona", "Naproxeno", "Nelfinavir ", "Neomicina ", "Neostigmina", "Nevirapina ",
				"Nicotinamida ", "Nifedipina", "Nimodipina", "Nistatina", "Nitrato ", "Nitrazepan", "Nitrito ",
				"Nitrofurantoína", "Nitrofurazona", "Nitroglicerina: ", "Nitroprussiato ", "Noradrenalina ",
				"Noretisterona ", "Norfloxacina", "Nortriptilina", "Ofloxacina", "Olanzapina", "Olopatadina",
				"Omeprazole", "Ondansetron ", "Orciprenalina", "Orthoftaldehído", "Oseltamivir ", "Osseína ",
				"Oxaliplatina", "Oxibuprocaína", "Oxibutinina", "Oximetazolina", "Oxitocina ", "Paclitaxel",
				"Pancreatina", "Paracetamol ", "Paradiclorobenzeno, ", "Paroxetina", "Penicilamina", "Pentamidina ",
				"Pentoxifilina", "Permanganato ", "Permetrina", "Petidina ", "Pidolato ", "Pilocarpina", "Pimosida ",
				"Piperacilina ", "Pipotiazina", "Piracetam", "Pirantel", "Pirazinamida", "Pirenoxina", "Piretrinas",
				"Piridostigmina", "Piridoxina", "Pirimetamina ", "Piroxicam", "Podofilotoxina", "Polistireno ",
				"Potássio", "Pralidoxima", "Pravastatina", "Praziquantel", "Prednisolona", "Primaquina", "Probenecid:",
				"Procarbazina ", "Progesterona ", "Proguanil", "Promestrieno", "Prometazina", "Propacetamol ",
				"Propiltiouracilo", "Propofol", "Propranolol", "Protamina", "Protóxido ", "Quetiapina", "Quinina",
				"Raloxifeno", "Ramipril", "Ranitidina", "Repaglinida", "Retinol", "Ribomunyl", "Rifabutina ",
				"Rimexolona", "Ringer ", "Risperidona", "Ritodrina ", "Ritonavir ", "Rivastigmina", "Ropivacaína",
				"Rosevastatina", "Sacarina", "Salbutamol", "Salmeterol", "Saquinavir ", "Seconidazol", "Selegilina",
				"Selénio", "Sertralina", "Sevoflurano", "Shirmmer", "Sildenafil ", "Simvastatina", "Simvastatina ",
				"Sirolumus ", "Sulfadiazina", "Sulfadiazina ", "Sulfadoxina ", "Sulfassalazina ", "Sumatriptano",
				"Suxametónio ", "Tacrolimus ", "Tadalafil", "Talidomida ", "Tamoxifeno", "Tansulosina", "Telmisartan",
				"Tenofovir ", "Teofilina", "Terazosina", "Tetraciclina ", "Tiamina: ", "Tiapride", "Tibolona",
				"Ticlopidina", "Timolol", "Timolol ", "Tinidazol", "Tiocolquicósido", "Tioconazol", "Tiopental",
				"Tiossulfato ", "Tiotrópio", "Tocoferol: ", "Topiramato", "Tramadol", "Travoprost", "Tretinoina ",
				"Triancinolona", "Triclabendazole", "Trifluoperazina", "Trimetazidina", "Triple-sulfa",
				"Triptorrelina ", "Tropicamida", "Troxerutina", "Tuberculina ", "UTrazodona", "Valproato", "Valsartan",
				"Valsartan ", "Vancomicina ", "Vardenafil", "Varfarina", "Vecurónio", "Venlafaxina", "Verapamil",
				"Vinblastina", "Vincristina", "Zidovudina ", "Ziprasidona", "Zoledronico", "Zopiclona" });

		general.put("MEIO_AMBIENTE", new String[] { "natur", "meio ambiente", "habitát", " ecolog", "ambienta",
				" bioma ", "golfo", "fauna", " flora" });

		general.put("MEMBROS_CORPO",
				new String[] { "bícep", "tricep", "panturrilha", "perna", "braço", "cotovel", "calcanh", "tornozel",
						" dedo ", " dedos ", " unha", "falange", "femur", "tibia", "cotovelo", "membro superior",
						"membro inferior", "membros superiores", "membros inferiores" });

		general.put("MINERIOS",
				new String[] { "ferro", " aço ", "bronze", " cobre ", "chumbo", "níquel", " ouro ", "óleo", "resina",
						"alumin", "miner", "polimeto", "polipropileno", " diamante ", " hematita ", " magnetita ",
						" pirita ", " manganita ", " malaquita ", " bauxita ", " cassiteria ", " Rubi ", " safira ",
						" Esmeralda ", " Espinelio ", " turmalina ", " ametista ", " topazio ", " turqueza ",
						" quartzo " });

		general.put("MOLUSCO",
				new String[] { "molusco", "ostra", "caramujo", "polvo", " lula", "ventosa", "manto", "caracol",
						"caracois", "gastropode", "cefalopode", "radula", "bivalve", "escafopode", "malacologia",
						" mollusca " });

		general.put("MOVIMENTO", new String[] { " corre", " anda", " parar", "veloz", " lent", "rápid", "viagem",
				"acelera", "continu", "nadar", "voar", "vôo", "desloca", "movel ", " mover ", " mexe" });

		general.put("MIDIAS",
				new String[] { "tv ", "televis", "rádio", "blog", "rede socia", "vlog", "podcast", "jornal", "vídeo" });

		general.put("MUDANCA", new String[] { " difer", " alter", " perturb", " muda ", " mudan", " diverssif",
				" transforma", "metamorf", "muta", "diversa ", "diversas" });

		general.put("MUSICA_ESTILO",
				new String[] { "ritm", "som", "sonor", "canto", "canta", "percuss", "instrument", "música", "partitura",
						"tablatura", " acorde", "nota musical", "compasso", "sinfoni", "harmoni", "melodi", "rap",
						"rock", " pop ", " sertanejo ", " blues ", " jazz ", " MPB ", " funk ", " Música clássica " });
		general.put("NECESSIDADE_PRIMARIA",
				new String[] { "respira", " aliment", " água ", " sede ", " come ", " bebe " });
		general.put("NECESSIDADE_SECUNDARIA", new String[] { " casa", " morad", "calçado", "vestuário" });

		general.put("NIVEL_ENSINO", new String[] { "ensino básico", "ensino fundamental", "ensino médio", "universi",
				" mestr", " graduac", "especializa", "doutor" });

		general.put("NUMERO", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " um ", " dois ",
				" três ", " quatro ", " cinco ", " seis ", " sete ", " oito ", " nove ", " zero ", "primeir", "segund",
				"terceir", "quart", "quint", "sext", "sétim", "oitav", "nono", "décimo", "nona", "décimo primeiro",
				"undécimo", "décimo segundo", "duodécimo", "décimo terceiro", "décimo quarto", "décimo quinto",
				"décimo sexto", "décimo sétimo", "décimo oitavo", "décimo nono", "vigésimo", "vigésimo primeiro",
				"vigésimo segundo", "vigésimo terceiro", "vigésimo quarto", "vigésimo quinto", "vigésimo sexto",
				"vigésimo sétimo", "vigésimo oitavo", "vigésimo nono", "trigésimo", "quadragésimo ", "quinquagésimo",
				"sexagésimo", "septuagésimo ou setuagésimo", "octogésimo", "nonagésimo", "centésimo", "ducentésimo",
				"trecentésimo", "tricentésimo", "quadringentésimo", "quingentésimo", "sexcentésimo ou seiscentésimo",
				"septingentésimo ou setingentésimo", "octingentésimo ou octogentésimo",
				"noningentésimo ou nongentésimo", "milésimo", "décimo milésimo", "centésimo milésimo", "milionésimo",
				"bilionésimo", "trilionésimo", "quatrilionésimo", "quintilionésimo", "Sextilionésimo", "Septilionésimo",
				"Octilionésimo", "Nonilionésimo", "Decilionésimo" });

		general.put("OCEANO", new String[] { "atlântico", "indico", "pacífico", "glacial" });

		general.put("ODONTOLOGIA",
				new String[] { "odontologia", "canal", "aparelho", "placa dentária", "coroa", "cimento", "transplante",
						"chapa", "oclusão", "dentina", "esmalte", "molar", "infiltrado", "transdutor", "cintilografia",
						"angiografia", "mamografia", "discografia", "opacificação", "braquiterapia", "telerradiografia",
						"ventriculografia", "ultrassonografia", "ultrassom", "abreugrafia", "aortografia", "sievert" });

		general.put("ONDULATORIA", new String[] { "propagar", "propagacao", "propagado", "eletromagne", "dimensional",
				" vibra", "longitu", "transvers", " corda", " vácuo", "comprimento de onda", "onda" });

		general.put("ONCOLOGIA",
				new String[] { "exame", "sinal", "complexo", "hemoterapia", "posologia", "escleroterapia",
						"insulinoterapia", "hidroterapia", "poliquimioterapia", "lenitivo", "equoterapia",
						"betaterapia", "isopatia" });

		general.put("OTORRINOLARINGOLOGIA",
				new String[] { "soroterapia", "eletroterapia", "galvanismo", "bloqueio", "ecocardiograma",
						"eletrocardiograma", "ecocardiografia", "arritmia", "endocardite", "acinesia", "desfibrilador",
						"eletrocardiografia", "embriocardia", "hipersistolia", "hipossistolia" });

		general.put("OSSOS_HUMANOS",
				new String[] { "vértebra cervical", "atlas", "áxis", "torácica", "sacro", "cóccix", "esterno",
						"costelas ", "costelas flutuantes ", "costelas falsas", "occipital", "parietal", "frontal",
						"esfenoide", "etmoide", "clavícula ", "escápula", "úmero", "ulna", "carpais escafoide",
						"semilunar", "piramidal ", "pisiforme ", "trapézio", "trapezoide", "capitato", "hamato",
						"metacarpais", "falanges", "osso do quadril ", "ílio ", "ísquio", "fêmur", "patela", "fabela",
						"fíbula", "tíbia", "calcâneo", "tálus", "navicular", "cuneiforme ", "cuboide", "metatarsais",
						"falanges proximais", "distais", "estribo" });

		general.put("ORGANIZAR",
				new String[] { " processo ", "arruma", "metodologi", "organizado", "correta", "corretud", "retifica",
						"ajust", "corrig", " fila ", "enfileira", "agenda", "planeja", "BPMS", " BPM ", "administra" });

		general.put("ORIENTACAO_OBJETOS",
				new String[] { " POO ", "orientacao a objeto", "tipo de variável", "encapsulamento", " atributo",
						"instancia", " estatico", "método acessor", " geter", " seter", "interface", "superclasse",
						"subclasse", "classe pai", "classe filha", "heranca " });

		general.put("ORIGEM", new String[] { "copia", "clona", "gemeo", "xerox", "origina", "origem" });

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
				"Unidos", "Bielorrússia", "Honduras", "Tajiquistão", "Sérvia", "Áustria", "Suíça", "Israel", " Togo",
				" Leoa", "Bulgária", "Laus", "Paraguai", "Líbia", "Salvador", "Nicarágua", "Quirguistão", "Líbano",
				"Turcomenistão", "Singapura", "Dinamarca", "Finlândia", "Eslováquia", "Brazzaville", "Noruega",
				"Eritreia", "Palestina", "Rica", "Libéria", "Irlanda", "Zelândia", "Africana", "Mauritânia", "Cuaite",
				"Croácia", "Panamá", "Moldávia", "Geórgia", "Herzegovina", "Uruguai", "Mongólia", "Albânia", "Arménia",
				"Jamaica", "Lituânia", "Catar", "Namíbia", "Botsuana", "Lesoto", "Gâmbia", "Macedónia", "Eslovénia",
				"Gabão", "Letónia", "Bissau", "Cosovo", "Barém", "Suazilândia", "Tobago", "Equatorial", "Estónia",
				"Maurícia", "Chipre", "Jibuti", "Fiji", "Comores", "Butão", "Guiana", "Montenegro", "Salomão",
				"Luxemburgo", "Suriname", "Micronésia", "Maldivas", "Brunei", "Malta", "Bahamas", "Belize", "Islândia",
				"Barbados", "Vanuatu", "Príncipe", "Lúcia", "Quiribáti", "Granadinas", "Tonga", "Granada", "Barbuda",
				"Seicheles", "Andorra", "Dominica", "Neves", "Samoa", "Marechal", "Mónaco", "Listenstaine", "Marinho",
				"Palau", "Nauru", "Tuvalu", "Vaticano", " ONU ", " OTAN ", " OEA ", "Uniao Europeia", "Nações unidas",
				"diplomacia", "embaixad" });

		general.put("PEIXE", new String[] { "aquário", "barbatana", "guelra", "branqui", "nadadeira", "escama",
				" pesca", "aquacultura", "tubarao", "pesqueir" });

		general.put("PERIGO",
				new String[] { "risco", "arriscado", "fugir", " medo ", "predador", "caça", "defesa", "escapar",
						"ameaca", "autopreservao", "legitima defesa", "defender", " fuga ", " perda", " dano ",
						"danifi", "ferir", "ferid", "ferrimento", "feroz", "indoma", "mortal", "critico", "criticidade",
						"inflamavel", "danger", "explosivo", "periculo", "arisca", "imprevisi", "afasta",
						"distanciamento", "perigos", "fatal" });

		general.put("PERGUNTA", new String[] { " entrevista", " pesquisa", " consulta ", "explora", "entende",
				"questiona", "compreend", "hipotese", "estud", "logia", " conhec", " teoria ", "?" });

		general.put("PENSAMENTO",
				new String[] { "idéia", "pensamento", "filósofo", "filosofia", "pergunta", "explica", "imagina",
						"sonho", "deduz", "criar", "criativ", "fantasi", "realidade", "pensar", "ilusio", "ilusor",
						"ilusao", "acredita", "crenca", " pensador", " pensa ", " sonha", "mental", " crítica ",
						" idéia ", " senso ", "lógica", "deducao" });

		general.put("PLANETA", new String[] { " terra ", " marte ", " vênus ", " mercurio ", " plutao ", " netuno ",
				" saturno ", " urano ", " jupiter ", " astro ", " esférico ", " mundo ", " corpo celeste " });

		general.put("PLANO",
				new String[] { "planejar", "pdca", "organizar", "medir", "controle", "estratég", "execucao", "esquema",
						"reuniao", "planejame", "roadmap", "acompanhamen", "scrum", "burningdown", "diagrama de pareto",
						"project", "relatorio", "orcamento", "budget", "orcado", "cumprimento de meta", " esboço ",
						" esquema ", " proposta " });

		general.put("PLANTA",
				new String[] { "caule", "flor", "folha", "semente", "cotiledone", " talo ", " seiva ", "clorofila",
						"cloroplasto", "photosíntese", " pólem ", "poliniz", "enchert", " galho ", "espinho", "casca",
						"frut", "adubo", "rosas", "bromeli", "veget" });

		general.put("PSIQUIATRIA",
				new String[] { "loucura", "louco", "mania", "psiquiatra", "traumatismo", "dissociação", "melancolia",
						"psicanálise", "psiquiatria", "inteligência", "juízo", "conceito", "espírito", "alma", "razão",
						"mente", "ser", "intelecto", "tino", "raciocínio", "sentido", "discernimento", "psicologista",
						"psicólogo", "animismo", "metafísico", "ideólogo", "político", "craniólogo",
						"transcedentalista" });

		general.put("POESIA",
				new String[] { "lírico", "poetic", "semiologic", "épico", "métrica", "liturgi", " hino ", " salmo ",
						" sura ", "hádice", "gregorian", "retóric", " verso", " prosa ", " eloquen", "norma culta",
						" soneto" });

		general.put("POLUICAO",
				new String[] { " lixo ", "detrito", "sujeira", " polui", " poluent", " refugo", "escória", " recicla",
						" chorume", "aterro sanitario", "descarte de", "coleta seletiva", " contaminação ",
						" impureza ", " degeneração", " degradação " });

		general.put("POLITICA",
				new String[] { "sociedade", "politica", "filosof", " lei ", "govern", "partid", "prefeit", " ong",
						"parlament", "candidat", "direito", "eleição", " voto ", "eleitor", " minist", " rei ",
						"rainha", "presidente", "ditador", "ditadura", "despot", "impera", "imperio", " rein", "cidada",
						"social", "sociais", "militan", "partido poli", "partidos polit", "sindicato", "sindicalis",
						"conselho de classe", "democra", "globaliz", "judicial" });

		general.put("PONTOS_CARDEAIS", new String[] { "norte", " sul", "sulista", "leste", " oeste ", "Setentrional",
				" Meridional ", " Oriente ", " Ocidente " });

		general.put("PONTOS_COLATERAIS",
				new String[] { " nordeste", "nordestin", "sudest", "sudoest", "noroeste", " NE ", " SE " });

		general.put("POSICIONAMENTO",
				new String[] { " perto ", " longe ", " alto ", " baixo ", " ir ", "vir ", " viagem ", " partir ",
						" vertical ", " horizont", "adjace", "adjunt", "esquerd", "direit", "cima", "lado", " atras ",
						"proximo", "distante", "dentro", "fora", "intern", "extern" });

		general.put("POVO", new String[] { " povo ", "população", "comunidade", "morador", "habitante", " nação ",
				" raça ", "etni", "etno", "cracia", "pardo", " afro" });

		general.put("POVOS_ANTIGOS",
				new String[] { "greco", "roman", "nordic", "mesopot", "sumer", "egip", "barbar", "indio" });

		general.put("PINTURA",
				new String[] { "tela", "gravura", "desenho", "rabisco", "pintar", "tinta", "obra de arte", "quadro",
						"printing", "printer", "blueprint", "fingerprint", "brush", "imagem", "imagens", "camera",
						"pincel", "tingi", "pintor" });

		general.put("PRACA", new String[] { "banco da praça", " coreto", "fonte de água", "chafaris", "parques",
				"jardim", "calçada" });

		general.put("PRAGAS", new String[] { "praga", "lavoura", "platio", "transmissão de doença", "vetores de doença",
				"vetor de doenç", "controle de praga", "defensivo", "agrotóxico" });

		general.put("PRISAO",
				new String[] { "preso", "julga", "pena ", "penal", "presídi", "cadeia", " cela ", "captura", "algema",
						"encarcera", "prision", "penitenci", "delegacia", "detetive", "detetado", " confina", "condena",
						" prender ", "cativeiro" });

		general.put("PROBLEMAS_HUMANOS", new String[] { "fome", "extinca", "consumis", "guerra", "desigualdade",
				"marginal", "polui", "lixo", "degradacao", "contamina", "tolerancia", " paz " });

		general.put("PROPRIDADE", new String[] { "posse", "propriedade", "pertence", "dono", "patrimonio", "recurso",
				"proprietario", "senhor", "possui" });

		general.put("PROGRESSAO",
				new String[] { " prosper", " cresci", " avanç", " cresce", " progress", "retrogra", " atras",
						"em frente", "fortalec", "enriquec", "retard", "diminui", "decai", "retrocesso", "regresso" });

		general.put("PROSTITUICAO",
				new String[] { "prostituta", "gigolo", "iniciacao sexual", "sexo pago", "doencas venerias", "promiscuo",
						"promisquidade", "mesalina", "cortesas", "hetera", "doenca sexual", "sexualmente transmissivel",
						"turismo sexual", "pedofil", "garotas de programa", "garota de programa", "garoto de programa",
						"garotos de programa", "striptease", " motel", " moteis", "prostibulo", "casa noturna",
						"assedio sexual", "erotismo", "erotic", "sexshopp", "fetiche", "sexo casual",
						"traicao conjugal", "sexo fora do casamento", "travesti", "transformista" });

		general.put("QUANTIDADE", new String[] { "muito", "pouco", "demais", "excesso", "estrapola", "acaba", " falta ",
				"suficiente", "máxim", "mínim", "normali", "equacion", "ajusta", "singul", "plural" });

		general.put("QUALIDADE_ADJETIVO", new String[] { "bom", "ruim", "eficaz", "satisfatório", "satisfaz", "atende",
				"impressiona", "assusta", " boa ", "péssima", " péssimo ", "satisfeit", "qualidade", "adorável",
				"afável", "afetivo", "agradável", "ajuizado", "alegre", "altruísta", "amável", "amigável", "amoroso",
				"aplicado", "assertivo", "atencioso", "atento", "autêntico", "aventureiro", "bacana", "benévolo",
				"bondoso", "brioso", "calmo", "carinhoso", "carismático", "caritativo", "cavalheiro", "cívico",
				"civilizado", "companheiro", "compreensivo", "comunicativo", "confiante", "confiável", "consciencioso",
				"corajoso", "cordial", "cortês", "credível", "criativo", "criterioso", "cuidadoso", "curioso",
				"decente", "decoroso", "dedicado", "descontraído", "desenvolto", "determinado", "digno", "diligente",
				"disciplinado", "disponível", "divertido", "doce", "educado", "eficiente", "eloquente", "empático",
				"empenhado", "empreendedor", "encantador", "engraçado", "entusiasta", "escrupuloso", "esforçado",
				"esmerado", "esperançoso", "esplêndido", "excelente", "extraordinário", "extrovertido", "feliz", "fiel",
				" fofo ", "franco", "generoso", "gentil", "genuíno", "habilidoso", "honesto", "honrado", "honroso",
				"humanitário", "humilde", "idôneo", "imparcial", "independente", "inovador", "íntegro", "inteligente",
				"inventivo", "justo", "leal", "legal", "livre", "maduro", "maravilhoso", "meigo", "modesto", "natural",
				"nobre", "observador", "otimista", "ousado", "pacato", "perfeccionista", "perseverante", "persistente",
				"perspicaz", "ponderado", "pontual", "preocupado", "preparado", "prestativo", "prestável", "proativo",
				"produtivo", "prudente", "racional", "respeitador", "responsável", "sábio", "sagaz", "sensato",
				"sensível", "simpático", "sincero", "solícito", "solidário", "sossegado", "ternurento", "tolerante",
				"tranquilo", "transparente", "valente", "valoroso", "verdadeiro", "zeloso" });

		general.put("QUIMICA",
				new String[] { "químic", "átomo", "atomi", "elétron", "próton", "mistura", "reação", "composto",
						"ácido ", "ácida ", "acidez", " alcalin", "básico", "eletró", "óxid", "quimica inorganica",
						"quimica organica", "alquimia", "pedra filosofal", "amino", "carbon", "destila", "decanta",
						" neutron ", " ion ", " cation ", " anion ", " catodo ", " anodo ", "glicose", " ph " });

		general.put("REACAO_QUIMICA",
				new String[] { " agente ", " reagente ", "composicao quimica", "molecula", "endotermic", "esotermic",
						"lavoisier", " prost ", " dalton ", " valencia ", "isobaro", "isotopo", "isotono",
						"balanceamento", "decomposicao", "oxireducao", "cataliza", "oxigen", " 1° membro ",
						" 2° membro " });

		general.put("REDES_SOCIAIS",
				new String[] { "facebook", "twitter", "linkedin", "instagram", "youtube", "snapchat", "orkut",
						"rede social", "redes sociais", "midias sociais", "midia social", " whatsapp ", " tinder ",
						" skype ", " facebook messenger" });

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
				new String[] { " deus", " fé ", "devoção", "devot", "pecado", "espirito", "messias", " alma ", "crença",
						"protestant", "cristian", "maometis", "uband", "budis", "xitois", "islamis", "sagrado",
						" sacro", " sacra", " culto", " cultu" });

		general.put("REINOS_ANIMAIS", new String[] { " animalia", " protista", " monera", "fungi", "plantae",
				"carnivoro", "onivoro", "reino animal", "protozo" });

		general.put("RURAL",
				new String[] { " campo", "fazenda", "sítio", "gado", "rebanho", "interior", "chácara", "campestre",
						"rural", "vaquejada", "carroça", "engenho", "cercado", "curral", "estrada de barro", "frango",
						"granja", "cocheira", "pilao", "moinho", "vacaria", "boiadeir", " peao ", "pocilga", " roça",
						"pomar", " orta ", "canavial", "colheita", "safra", "plantio", "estacao das chuvas",
						"festa junina", "agricultor", "caipira", "trator", " arado", "enxada", " pá ", "ciscador",
						" foice ", " machado ", "motoserra", "sertao", "sertanejo", "chapeu de palha", " arreio",
						" chicote", "chibata", "colhedeira", "agranomo", "cooperativa", " rodeio" });

		general.put("SAUDE", new String[] { "saúde", "saudável", "curado", "forte", "bem-estar", "vacina", "robust",
				"medicin", "anestesi", "enferma", "saúde coletiva", "serviços de saúde", "paciente" });

		general.put("SEGURANCA", new String[] { "segurança", "proteção", " arma ", "assalt", "política pública",
				"ir e vir", "latro", "furto", "roubo", "rouba", "belico", " protegid " });

		general.put("SEGURANCA_INFORMACAO",
				new String[] { "senha", "password", "criptogra", "cripta", "ataque", "roubo de", "engenharia social",
						"autentica", "protocolo de segurança", "antivrirus", "spam", "auth", " jwt ", "certificad" });

		general.put("SEXO",
				new String[] { "reprodução", "reprodut", "sexo", " coito", "relação sexual", "acasala", "cortejo",
						"macho", "fêmea", "gestação", "gravidês", " gravida ", " prazer", " fornica", " mascul",
						" femini", " parto ", "fecunda", "fecundo", " ovulo", " feto", "hermafrodita", "abort", "sexua",
						" engravidar ", " engravid" });

		general.put("SELECAO", new String[] { "escolha", "filtr", " selec", " selet", " individu", " descart",
				" separa", " catalog" });

		general.put("PERIODICIDADE",
				new String[] { "segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo", "dominical",
						"semanal", " diário ", "mensal", "periódico", "vespertino", "horario", "noturn", "diurn",
						"repetitiv", "manhã", "tarde", "noite", "madrugada", "alvorada", "alvorece", "janeiro",
						"fevereiro", " março ", " abril ", " maio ", "junho", "julho", "agosto", "setembro", "outubro",
						"novembro", "dezembro", " rotin", "aleatori", "period" });

		general.put("PROFISSAO_EXATA_NATUREZA",
				new String[] { "Engenheiro", "Programador", "Desenvolvedor", "Analista", "Arquiteto ", "Matemático",
						"Físico", "Químico", "Bacharel", "Estatísti", "Eletrotécni", "Mecânico", "Astrônomo", "Bioeng",
						"Geógraf", "Astronauta", "Agrônom", "Topógraf", "Cartograf" });

		general.put("PROFISSAO_HUMANA_ARTE",
				new String[] { "Advogado", "Administrador", "Contador", "Secretári", "Sociólogo", "Pedagog", "Cineasta",
						"Escritor", "Compositor", "Músico", "Musicista", "Arqueólog", "Historiador", "Antropólogo" });

		general.put("PROFISSAO_BIOLOGIA_SAUDE",
				new String[] { "Médic", "Psicólog", "Psiquiatr", "Enfermeir", "Pediatr", "Dentist", "Cirurgi",
						"Oftalmologi", "Otorrinolaringologi", "Legist", "Neurocirurgi", "Anestesisti", "Veterinári",
						"Obstetr", "Dermatologi", "Instrumenti", "Neurologi", "Cardiologi", "Pneumologi", "Oncologi",
						"Farmac" });

		general.put("SENTIDOS",
				new String[] { "audição", "ouvir", "olfat", "cheir", "gosto", "paladar", "toque", "manusear", " tato ",
						"visão", " ver ", " olhar", "olha", "tocar", " pele ", "retina", "visuali", "degust", "degluti",
						"intui", "sensori", "percepc" });

		general.put("SENTIMENTO",
				new String[] { "medo", "raiva", "alegria", "frustra", "eufori", "pavor", "esperanç", "afeti", "afeic",
						"apego", "carinho", "depress", "desist", "trist", "ódio", "fobia", "averss", "Amor", "bondos",
						"benig", "malign", "sentimento", "sentir", " emocao ", "emocion" });

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
				"asiático", "socialis", "primitivo", "mercantil" });

		general.put("SOFTWARE", new String[] { "software", "teste de software", "mobile", "tecnologia de informação",
				"programa de computador", "aplicativo", "modelagem de software", "virtual", "arquivo", "app ",
				"relacional", "development", "developer", " sistemas de informacao", " computac", " computad" });

		general.put("SOM",
				new String[] { "onda sonora", "sonoridade", "barrulh", "falante", "fones", "sonar", " eco ", "reverbe",
						"grave", "agudo", "timbre", "distorção", "mixagem", "masteriza", "equaliza", "compress",
						"amplifica", " sinal ", "ruido", "silenci", "calar", "calado", " mudo ", " surdo ", "surdez" });

		general.put("SUBSTANCIAS",
				new String[] { "proteina", "ácido nucleico", "citosi", "guanina", "uracil", "osmótic", "linfocito",
						"leucocito", "hemacea", "hemoglobina", "vitamina", " salga", "salino", "salini", "salina",
						" sal ", });

		general.put("TECNOLOGIA",
				new String[] { "tecnologi", "foguete", "espacial", " nasa ", "celular", "desenvolvimento", "técnica",
						" novo ", "inova", "metodo", "bluetooth", " internet ", " redes de computadores", " informat",
						" hardware ", " software ", " inteligencia artificial " });

		general.put("TEATRO", new String[] { "drama", "comédia", "palco", "peça teatral", "musical", "ópera", "roteiro",
				"interpret", "atriz", " ator", " cena ", " cenário", " cenic", "persona", "personif" });

		general.put("TELA_COMPUTADOR", new String[] { "pixel", " png ", "bitmap", "JPEG", "BMP", "resolução",
				"densidade", "layer", "RGB", "tela plana", "cromati", " tela de", " led ", "cristal liquid" });

		general.put("TELECOMUNICACAO", new String[] { "satélite", "telefone", "telegrafo", "onda eletromagnetica",
				"amplitude modulada", "frequencia", "transmiss", "transmit", "converss", "tcp/ip", "telecomunicac" });

		general.put("TEMPO",
				new String[] { "semana", "mês", "ano ", "década", "século", "passado", "presente", "futuro", "bimestr",
						"trimestr", "semestr", "anual", "bienal", "contemporâ", "anterior", "posterior", "agora",
						"depois", "efemero", "volatil", "rapido", "fugaz", "fugacid", "tempo", "minuto", "segundo",
						"hora", "milisegundos" });

		general.put("TERRENO",
				new String[] { "areia", "terra", "rocha", "rochos", "sediment", "terreno", "geólogo", "geologi",
						"relevo", "planalto", "montanha", "planície", "petro", "petri", "areno", "lama", "pantano",
						" barro ", "saibro", "argila", "argilos", "quartzo", "bauxita", " caverna " });

		general.put("TIPOS_ESPORTE",
				new String[] { "Futebol", "Basquete", "Volei", "Tenis", " Tenis de", "ping pong", "Handball", "Handbol",
						"Golfe", " Beisebol ", "Hoquei", "Rugby", "Criquete", "Boxe", "Atletismo", "Natacao", "Futsal",
						"Karate", "Judo", "Futevolei", "Ciclismo", "Boliche", "Xadrez", "Dama", "Jiu Jitsu" });

		general.put("TORAX", new String[] { " seio ", " mama ", "pulmão", "torax", "homoplata", "ombro", "peito" });

		general.put("TRANSPORTE", new String[] { "carro", "bicicl", "transport", "ferrovia", "marítimo", "aéreo",
				"nave", "naval", "terrestre", "submarino", "barco", " bote ", "jangad", "caravela", "avião", "aeronave",
				"helicóptero", " jato", "piloto", "condutor", "aeroeapcial", "veículo", "rota", "caminho", "trajeto",
				"viaj", "percurso", "corrida", " transpor ", "ultrapassa", "transit", "transeunte" });

		general.put("TRANSPORTE_PUBLICO", new String[] { "metrô", "ônibus", "trem", "caminhão", "pau de arara",
				"lotação", "balsa", "rodovia", "estrada", "ponte", "túnel" });

		general.put("UNIDADES_MEDIDA",
				new String[] { "m/s", "km/h", "kg", "m2", "m3", " wh ", " ah ", " hp ", "btu", "polegada", "litro",
						" quilo", " mega", " nano", " pico", " zeta", " mili", " micro", "macro", "/ml", "%", "mg/kg",
						"mg/l", "lpsf/gq", "d+lps=", "d/h", " km", " cm", " mm", " ml", "hecto", "deca", "deci",
						"centi", "mili", "micro", "nano", "pico", "femto", "atto", "zepto", "yocto", "metro",
						"quilograma", "ampère", "mol", "radiano" });

		general.put("UNIVERSO",
				new String[] { "univers", "matéria", "dimens", "big bang", "matéria escura", "relativ", });

		general.put("URBANO",
				new String[] { "espaços", "urbaniza", "cidade projetada", "cidade inteligente", "licenciamento",
						"paisagis", "revitaliz", "suburb", "capitais", "mobilidade", "urban", "lopole", "pólis",
						"centro", "esgoto", " coleta", "parque", "cotidian", "metropol" });

		general.put("UTENSILIOS",
				new String[] { " pote ", " copo ", "canec", "xicara", "balde", "barril", "tonel ", "frasco", " tubo ",
						"kitassato", "instrumentacao", "bico de bunsen", "recipiente", " garfo ", " faca ", " colher ",
						" talher", " pires ", " louça ", "bandeja", "escorredor", "panela", "frigideira", "calice",
						" objeto ", " ferramentas ", " ferramenta " });

		general.put("VALORES",
				new String[] { "valor", "ética", "moral", "princípio", "convic", "virtu", "hábito", "vício",
						"etica profissional", "honest", "corrup", "desvio de verba", "desvio de campanha", "propina",
						"escandalo", "ilicitu", " etico", "carteis", "trust", "corromp", "fraud", "compra de voto",
						"venda de voto", " multa", "entidade", "instituic" });

		general.put("VEGETACAO",
				new String[] { "floresta", "caatinga", "cerrado", "tundra", "savana", "taiga", "xerofit", "mangue",
						"pantan", "botânic", "árvore", "samambaia", " alga ", " mata ", "vegeta", "selvagem",
						"silvestre" });

		general.put("VENTO",
				new String[] { " vento ", "tornado", "furacão", "tormenta", " brisa ", "ciclone", " ventania ",
						" maresia ", " eolo ", " ar ", " moncao ", " minuano ", " vendaval ", " ventaneira ",
						" soprador " });

		general.put("VERDADE",
				new String[] { "certificado", "autent", "certidão", "cartório", "tabeli", "veridic", " puro ", "pureza",
						"mentira", "falsi", "engana", "engano", "equivoc", "histeri", "art. 171", "verific",
						"pseudo" });

		general.put("VESTUARIO",
				new String[] { "camisa", "shorts", "cueca", "calça", "sutiã", "chapéu", "boné", " touca ", " meia ",
						"camiseta", " terno ", "gravata", " meias ", "roupas", "moda", "tecido", "algodao", " seda ",
						"couro", "roupa sintetica", "constur", "adorno", "veste", "vestuario", "vestiment", "camufla",
						"joias", "malhas" });

		general.put("VIA_PUBLICA", new String[] { "asfalto", "iluminação", "poste", "parada", "ponto de ", " via ",
				"semáforo", "rede elétrica", "distribuição", "lampada", "corredor de", "táxi", "viaduto", "praça" });

		general.put("VIDA_FASE",
				new String[] { " ovo ", " larva", " pupa ", "criança", "infant", "jovem", "adulto", "adulta", "juven",
						"idoso", "senil", "idosa", "idade", "bêbe", "recem nasc", "puberdade", "adolesc", "nasce",
						"crescimento", "morte", "sepult", "enterro", "enterra", " cremar ", "cremacao", " falec",
						"falenci", " vitali", "amadurec", "envelhec", " matura" });

		general.put("VIDA_GENERO",
				new String[] { "homem", "homens", "mulher", "menino", "menina", "rapaz", "moça", "homosexual", " lesbi",
						"maxismo", "maxista", "feminismo", "feminista", "identidade de genero", "masculin", "femini",
						"sexual" });

		general.put("VIGILANCIA",
				new String[] { " vigia", " camera", " monitora", " alarm", "carro forte", "segurança patrimo",
						"vigilan", " olhar ", "observa", " confere", "conferen", "guarda", "seguranca", "inspecio",
						"inspecao" });

		general.put("VIOLENCIA",
				new String[] { " briga", "agreção", "agredir", "agress", "queixa", "violên", "confront", "espanca",
						"bater", " soco ", "chute", " golpe", " socar ", " pancada ", "matar", "assassina", "estupr",
						"violar", "bulling", "forca fisica", "forca bruta", "cotovelada", "mata leao", "ponta pe",
						"paulada", "acao contundente", "esfaque", "facada", "queixa policial", "boletim de ocorrencia",
						"disk denuncia", "conflito armado", "bala perdida", "troca de tiro", "maria da penha",
						"homicidio", "feminicidio", "suicidio", "crime violento", "crime barbaro", "imoral", "gangue",
						"quadrilha", "crime organizado", "traficante", "trafico", "milicia", "comando vermelho",
						"faccao", "faccoes", "grupo de extermineo", "grupos de extermineo" });

		general.put("ZOOTECNIA", new String[] { "aclimação", "domesticação", "estabulação", "amansadela   ",
				"amansadura", "repasse", "criação", "domesticidade", "veterinária", "amansia", "mestiçagem",
				"avicultura", "suinocultura", "bovinocultura", "avicultor", "zagal", "aviculário", "vaqueiro",
				"criador", "pegureiro", "peão", "boiadeiro", "domador", "amansador", "domesticador", "arrebanhador",
				"avícola", "redil", "aprisco", "ovil", "estábulo", "estrebaria", "cavalariça", "oviário", "jaula",
				"presépio", "toca", "gaiola", "canil", "viveiro", "alberca", "passareira", "alverca", "plantel",
				"sementeira", "seminário   ", "criadeiro", "colmeal", "apiário", "alveário", "ménagerie", "piscina",
				"corte", "aviário", "colmeia", "cortiço", "invernada", "silha", "animalicídio", "arca de noé",
				"colméia", "alveitar", "veterinário", "zooiatra", "zootécnico", "apífilohipiatra" });

		// Tornando tudo minusculo
		Map<String, String[]> generalAux = new TreeMap<String, String[]>();
		for (String key : general.keySet()) {
			String[] values = general.get(key);
			String[] valuesAux = new String[values.length];
			for (int i = 0; i < valuesAux.length; i++) {
				valuesAux[i] = values[i].toLowerCase();
			}
			generalAux.put(key.toLowerCase(), valuesAux);
		}

		general.clear();
		general = generalAux;

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

					key = unaccent(key.toLowerCase());

					boolean hasConcept = false;
					float countThe = 0;

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
//						line.append(";" + (countThe / txt.split(" ").length));
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
