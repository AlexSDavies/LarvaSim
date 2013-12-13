
function meta = getStatMeta()

meta.bearingBins = 0:11.25:348.75;

meta.turnProbBins = -pi+pi/12:pi/6:pi-pi/12;

meta.turnCumulativeBins = 11.25:22.5:168.75;

meta.oneCastLabels = {'H','L'};
meta.twoCastLabels = {'HH','LH','HL','LL'};
meta.threeCastLabels = {'HHH','HLH','LHH','LLH','HHL','HLL','LHL','LLL'};

meta.distanceTics = 2.5:5:97.5;