# Modelo Publica-Suscribe

Práctica realizada para la asignatura Computación Distribuida del Grado en Ingeniería Informática de la USC.

# Problema a solucionar

Durante la monitorización en tiempo real de un paciente en una unidad de cuidados coronarios se adquiere información de su frecuencia cardíaca. En el siguiente archivo https://archive.physionet.org/challenge/2002/rr1.txt

Disponemos de una serie temporal correspondiente a la distancia en segundos entre cada dos latidos consecutivos (lo que habitualmente se denomina intervalo RR) correspondiente a un paciente monitorizado durante varias horas.

Si asumimos que cada dato se genera con una frecuencia de aproximadamente un segundo, se busca diseñar una aplicación distribuida que se rija por el modelo publica-suscribe para la transmisión en tiempo real de dicha señal usando para ello java RMI mediante callbacks. 

Para ello se diseñará un servidor que tan pronto arranque proceda a leer línea a línea el archivo con una frecuencia de una línea por segundo y enviará una copia del dato leído a cada uno de los clientes que se hayan suscrito. 

Cada cliente debe de suscribirse durante un tiempo en el servidor (expresado en segundos) y pasado dicho tiempo dejará de recibir información. 

Adicionalmente existe un mecanismo para renovar la suscripción del cliente en el servidor. Cada vez que dicha suscripción se renueve, el tiempo de suscripción se fijará de nuevo en el valor aportado por el usuario. 

Finalmente, el cliente es capaz de representar gráficamente la información recibida, mostrando en todo momento el último minuto de señal recibido.
