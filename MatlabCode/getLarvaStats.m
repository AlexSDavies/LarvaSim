
function larvaStats = getLarvaStats()

larvaStats.turnCumulativeProb = [0.01 0.03 0.08 0.17 0.33 0.52 0.75 1];
turnCumulativeTics = 11.25:22.5:168.75;

circalTics = 0:11.25:348.75;

larvaStats.bearingBeforeLeftTurns = [0	0	0	0	0	0	92	115	113	147	193	172	140	172	186	149	124	118	93	80	73	33	30	28	25	19	0	0	0	0	0	0];
larvaStats.bearingAfterLeftTurns = [67 89	104	131	120	144	163	177	166	129	126	129	103	74	89	67	57	62	41	30	30	24	22	0	0	0	0	0	0	20	36	40];

larvaStats.bearingBeforeRightTurns = [0	0	0	0	0	0	0	0	0	0	0	0	0	83	107	143	124	170	212	232	220	242	218	206	131	78	68	65	55	62	60	46];
larvaStats.bearingAfterRightTurns = [100	71	41	0	0	0	0	0	0	21	15	0	0	0	0	0	45	61	103	86	109	120	128	137	157	169	251	218	155	140	172	108];

larvaStats.bearingBeforeTurnsToHigh = [0	0	0	0	0	64	108	242	314	311	412	504	412	519	560	462	504	632	695	666	722	653	609	381	242	187	206	149	45	0	0	0];
larvaStats.bearingBeforeTurnsToLow = [0	0	0	0	0	0	0	0	0	0	63	106	228	291	415	371	383	348	300	262	236	125	124	90	45	0	0	0	0	0	0	0];

larvaStats.leftTurnProb = [0.38	0.23	0.14	0.14	0.11	0.53	0.56	0.85	0.95	0.86	0.70	0.58];
leftTurnTics = -165:20:165;

larvaStats.oneCastRatios = [0.77 0.23];
oneCastLabels = {'H','L'};

larvaStats.twoCastRatios = [0.36 0.38 0.12 0.14];
twoCastLabels = {'HH','LH','HL','LL'};

larvaStats.threeCastRatios = [0.38 0.14 0.05 0.14 0.06 0.03 0.05 0.15];
threeCastLabels = {'HHH','HLH','LHH','LLH','HHL','HLL','LHL','LLL'};

