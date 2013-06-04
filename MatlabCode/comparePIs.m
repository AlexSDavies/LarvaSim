function parameters = comparePIs(uniqueName,numFiles,numLarvae,parameterName)

PIs = zeros(numLarvae,numFiles);

h = waitbar(1/(numFiles*numLarvae),'Processing...');

for f = 1:numFiles
	parameters(f) = readParameters(['parameters_' uniqueName num2str(f) '_Larva_1']);
	for l = 1:numLarvae
		PIs(:,f) = getPI([uniqueName num2str(f) '_'],numLarvae);
		waitbar((f*numLarvae+l)/(numFiles*numLarvae));
	end
end

close(h);

boxplot(PIs,[parameters.(parameterName)]);