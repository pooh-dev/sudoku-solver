using SudokuSolver.Algorithms;
using SudokuSolver.Domain;
using System.Threading.Channels;

namespace SudokuSolver.Services;

public class Solver
{
    private static Channel<Field> fieldsQuery = Channel.CreateUnbounded<Field>();

    private readonly List<ICellFinder> _cellFinders = new()
    {
        new CellFinderBySinglePossibleValue(),
        new CellFinderByUniquePossibleValue()
    };

    public async Task<Field> Solve()
    {
        while (await fieldsQuery.Reader.WaitToReadAsync())
        {
            if (fieldsQuery.Reader.TryRead(out var field)) 
            {
                bool isAnyCellOpen;
                do
                {
                    isAnyCellOpen = false;
                    foreach (var cellFinder in _cellFinders)
                    {
                        isAnyCellOpen |= OpenCellsUsing(cellFinder, field);
                    }
                } while (isAnyCellOpen);

                if (field.AreAllCellsOpen())
                {
                    return field;
                }

                // solution is not found
                // TODO: add logic to suggest a possible solution 
                return field;                
            }
        }

        throw new NotImplementedException();
    }

    public async ValueTask AddForSolving(Field field)
    {
        await fieldsQuery.Writer.WriteAsync(field);
    }

    private bool OpenCellsUsing(ICellFinder cellFinder, Field field)
    {
        var isAnyCellOpen = false;

        CellFinderResult cellFinderResult;
        do
        {
            cellFinderResult = cellFinder.Find(field.Cells);
            if (cellFinderResult.IsCellFound())
            {
                field.SetCellValue(cellFinderResult);
                isAnyCellOpen = true;
            }

        } while (cellFinderResult.IsCellFound());

        return isAnyCellOpen;
    }
}
