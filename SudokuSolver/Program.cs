using SudokuSolver.Domain;
using SudokuSolver.Services;

List<string> simplePuzzle = new()
{
    "0,1,3,0,8,5,0,0,6",
    "0,0,0,0,3,1,0,0,0",
    "0,0,7,6,0,0,0,0,0",
    "0,0,6,9,0,0,0,0,3",
    "1,7,0,0,0,0,0,2,5",
    "3,0,0,0,0,6,9,0,0",
    "0,0,0,0,0,2,4,0,0",
    "0,0,0,8,7,0,0,0,0",
    "9,0,0,5,6,0,3,1,0"
};

List<string> difficultPuzzle = new()
{
    "0,9,0,7,8,5,0,1,0",
    "0,0,0,4,0,0,6,0,0",
    "0,0,0,0,0,0,0,0,0",
    "0,3,0,0,0,0,0,0,0",
    "9,0,0,0,2,0,0,0,4",
    "0,0,0,0,0,0,0,5,0",
    "0,0,0,0,0,0,0,0,0",
    "0,0,9,0,0,8,0,0,0",
    "0,4,0,3,6,1,0,7,0"
};

Field inputField = new();
inputField.Init(difficultPuzzle);

Solver solver = new();
await solver.AddForSolving(inputField);
var outputField = await solver.Solve();

Console.WriteLine(outputField);

Console.Read();