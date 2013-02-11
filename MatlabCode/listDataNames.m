function listDataNames()

files = dir('../Data');
names = {files.name};


names = names(strncmpi('data',names,4));

names = regexprep(names,'\<[a-z]*_|_[0-9]*\>','');

names = unique(names);

for i = 1:length(names)
    disp(names(i));
end